package com.vittorhonorato.stockflow.service.impl;

import com.vittorhonorato.stockflow.dto.external.cnpja.CnpjaOfficeResponseDTO;
import com.vittorhonorato.stockflow.dto.request.FornecedorRequestDTO;
import com.vittorhonorato.stockflow.dto.response.FornecedorResponseDTO;
import com.vittorhonorato.stockflow.dto.response.ValidacaoDocumentoFornecedorResponseDTO;
import com.vittorhonorato.stockflow.entity.Fornecedor;
import com.vittorhonorato.stockflow.entity.SituacaoCadastral;
import com.vittorhonorato.stockflow.entity.TipoDocumento;
import com.vittorhonorato.stockflow.exception.fornecedores.DocumentoFornecedorDuplicadoException;
import com.vittorhonorato.stockflow.exception.fornecedores.DocumentoFornecedorInvalidoException;
import com.vittorhonorato.stockflow.exception.fornecedores.FornecedorNaoEncontradoException;
import com.vittorhonorato.stockflow.exception.fornecedores.SituacaoCadastralFornecedorInvalidaException;
import com.vittorhonorato.stockflow.integration.cnpj.CnpjaClient;
import com.vittorhonorato.stockflow.mapper.FornecedorMapper;
import com.vittorhonorato.stockflow.repository.FornecedorRepository;
import com.vittorhonorato.stockflow.service.FornecedorService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    private final CnpjaClient cnpjaClient;
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    public FornecedorServiceImpl(CnpjaClient cnpjaClient, FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.cnpjaClient = cnpjaClient;
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    @Override
    public FornecedorResponseDTO criar(FornecedorRequestDTO request) {
        String documentoNormalizado =  normalizarDocumento(request.documento());

        validarDocumentoParaCadastro(documentoNormalizado, request);

        Fornecedor fornecedor = fornecedorMapper.toEntity(request);
        fornecedor.setDocumento(documentoNormalizado);
        fornecedor.setTipoDocumento(TipoDocumento.CNPJ);
        fornecedor.setDataUltimaValidacao(LocalDateTime.now());

        Fornecedor fornecedorSaved = fornecedorRepository.save(fornecedor);

        return fornecedorMapper.toResponseDTO(fornecedorSaved);
    }

    @Override
    public List<FornecedorResponseDTO> listarTodos() {
        return fornecedorRepository.findAll().stream()
                .map(FornecedorMapper::toResponseDTO)
                .toList();
    }

    @Override
    public FornecedorResponseDTO buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
                .map(FornecedorMapper::toResponseDTO)
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado com o id: " + id));
    }

    @Override
    public FornecedorResponseDTO buscarPorDocumento(String documento) {
        String documentoNormalizado = normalizarDocumento(documento);

        return fornecedorRepository.findByDocumento(documentoNormalizado)
                .map(FornecedorMapper::toResponseDTO)
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado com o documento informado " + documentoNormalizado));
    }

    @Override
    public FornecedorResponseDTO atualizar(Long id, FornecedorRequestDTO request) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado com o id: " + id));

        String documentoNormalizado = normalizarDocumento(request.documento());

        boolean documentoFoiAlterado = !fornecedor.getDocumento().equals(documentoNormalizado);

        if (documentoFoiAlterado && fornecedorRepository.existsByDocumento(documentoNormalizado)) {
            throw new DocumentoFornecedorDuplicadoException("Já existe um fornecedor cadastrado com esse documento");
        }

        if (request.situacaoCadastral() != SituacaoCadastral.ATIVA) {
            throw new SituacaoCadastralFornecedorInvalidaException("Fornecedor não pode ser atualizado com situação cadastral diferente de ATIVA");
        }

        fornecedorMapper.updateEntityFromDTO(request, fornecedor);
        fornecedor.setDocumento(documentoNormalizado);

        Fornecedor fornecedorAtualizado = fornecedorRepository.save(fornecedor);

        return FornecedorMapper.toResponseDTO(fornecedorAtualizado);
    }

    @Override
    public void desativar(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado com o id: " + id));

        fornecedor.setAtivo(false);

        fornecedorRepository.save(fornecedor);
    }

    @Override
    public ValidacaoDocumentoFornecedorResponseDTO validarDocumento(String documento) {
        String documentoNormalizado = normalizarDocumento(documento);

        if(documentoNormalizado == null || documentoNormalizado.length() != 14) {
            return new ValidacaoDocumentoFornecedorResponseDTO(
                    null,
                    null,
                    documentoNormalizado,
                    null,
                    false,
                    false,
                    LocalDateTime.now()
            );
        }

        CnpjaOfficeResponseDTO response = cnpjaClient.consultarCnpj(documentoNormalizado);

        SituacaoCadastral situacaoCadastral = SituacaoCadastral.fromCodigo(response.status().id());

        boolean documentoValido = response.taxId() != null;
        boolean permiteCadastro = situacaoCadastral == SituacaoCadastral.ATIVA;

        return new ValidacaoDocumentoFornecedorResponseDTO(
                response.company().name(),
                TipoDocumento.CNPJ,
                response.taxId(),
                situacaoCadastral,
                documentoValido,
                permiteCadastro,
                LocalDateTime.now()
        );
    }

    private void validarDocumentoParaCadastro(
            String documentoNormalizado,
            FornecedorRequestDTO requestDTO
    ) {
        if(documentoNormalizado == null || documentoNormalizado.length() != 14) {
            throw new DocumentoFornecedorInvalidoException("CNPJ inválido");
        }

        if(fornecedorRepository.existsByDocumento(documentoNormalizado)) {
            throw new DocumentoFornecedorDuplicadoException("Já existe um fornecedor cadastrado com esse documento");
        }

        if(requestDTO.situacaoCadastral() != SituacaoCadastral.ATIVA) {
            throw new SituacaoCadastralFornecedorInvalidaException("Fornecedor só pode ser cadastrado com situação cadastral ATIVA");
        }
    }

    private String normalizarDocumento(String documento) {
        if(documento == null) {
            return null;
        }

        return documento.replaceAll("\\D", "");
    }
}
