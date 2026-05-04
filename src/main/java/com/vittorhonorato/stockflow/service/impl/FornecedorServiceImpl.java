package com.vittorhonorato.stockflow.service.impl;

import com.vittorhonorato.stockflow.config.CacheNames;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @CacheEvict(cacheNames = CacheNames.FORNECEDORES_OPCOES, allEntries = true)
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
    public Page<FornecedorResponseDTO> listarTodos(Pageable pageable) {
        return fornecedorRepository.findAll(pageable)
                .map(fornecedorMapper::toResponseDTO);
    }

    @Override
    public FornecedorResponseDTO buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
                .map(fornecedorMapper::toResponseDTO)
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado com o id: " + id));
    }

    @Override
    public FornecedorResponseDTO buscarPorDocumento(String documento) {
        String documentoNormalizado = normalizarDocumento(documento);

        return fornecedorRepository.findByDocumento(documentoNormalizado)
                .map(fornecedorMapper::toResponseDTO)
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado com o documento informado " + documentoNormalizado));
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.FORNECEDORES_OPCOES, allEntries = true)
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

        return fornecedorMapper.toResponseDTO(fornecedorAtualizado);
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.FORNECEDORES_OPCOES, allEntries = true)
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
                    null,
                    null,
                    null,
                    null,
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
        String email = extrairEmail(response);
        String telefone = extrairTelefone(response);
        String street = response.address() != null ? response.address().street() : null;
        String city = response.address() != null ? response.address().city() : null;
        String state = response.address() != null ? response.address().state() : null;

        return new ValidacaoDocumentoFornecedorResponseDTO(
                response.company().name(),
                TipoDocumento.CNPJ,
                response.taxId(),
                email,
                telefone,
                street,
                city,
                state,
                situacaoCadastral,
                documentoValido,
                permiteCadastro,
                LocalDateTime.now()
        );
    }

    @Override
    @Cacheable(cacheNames = CacheNames.FORNECEDORES_OPCOES)
    public List<FornecedorResponseDTO> findAllOptions() {
        return fornecedorRepository.findByAtivoTrueOrderByNomeAsc()
                .stream()
                .map(fornecedorMapper::toResponseDTO)
                .toList();
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

    private String extrairEmail(CnpjaOfficeResponseDTO response) {
        if (response.emails() == null || response.emails().isEmpty()) {
            return null;
        }

        return response.emails().stream()
                .map(email -> email.address())
                .filter(address -> address != null && !address.isBlank())
                .findFirst()
                .orElse(null);
    }

    private String extrairTelefone(CnpjaOfficeResponseDTO response) {
        if (response.phones() == null || response.phones().isEmpty()) {
            return null;
        }

        return response.phones().stream()
                .map(phone -> {
                    if (phone.number() == null || phone.number().isBlank()) {
                        return null;
                    }

                    if (phone.area() == null || phone.area().isBlank()) {
                        return phone.number();
                    }

                    return phone.area() + phone.number();
                })
                .filter(number -> number != null && !number.isBlank())
                .findFirst()
                .orElse(null);
    }
}
