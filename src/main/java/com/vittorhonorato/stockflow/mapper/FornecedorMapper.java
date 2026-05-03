package com.vittorhonorato.stockflow.mapper;

import com.vittorhonorato.stockflow.dto.request.FornecedorRequestDTO;
import com.vittorhonorato.stockflow.dto.response.FornecedorResponseDTO;
import com.vittorhonorato.stockflow.entity.Fornecedor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FornecedorMapper {

    public Fornecedor toEntity(FornecedorRequestDTO requestDTO) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setNome(requestDTO.nome());
        fornecedor.setTipoDocumento(requestDTO.tipoDocumento());
        fornecedor.setDocumento(requestDTO.documento());
        fornecedor.setSituacaoCadastral(requestDTO.situacaoCadastral());
        fornecedor.setEmail(requestDTO.email());
        fornecedor.setTelefone(requestDTO.telefone());
        fornecedor.setStreet(requestDTO.street());
        fornecedor.setCity(requestDTO.city());
        fornecedor.setState(requestDTO.state());
        fornecedor.setAtivo(true);
        fornecedor.setDataUltimaValidacao(LocalDateTime.now());

        return fornecedor;
    }

    public FornecedorResponseDTO toResponseDTO(Fornecedor fornecedor) {
        return new FornecedorResponseDTO(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getTipoDocumento(),
                fornecedor.getDocumento(),
                fornecedor.getSituacaoCadastral(),
                fornecedor.getEmail(),
                fornecedor.getTelefone(),
                fornecedor.getStreet(),
                fornecedor.getCity(),
                fornecedor.getState(),
                fornecedor.getAtivo(),
                fornecedor.getDataUltimaValidacao(),
                fornecedor.getDataCriacao(),
                fornecedor.getDataAtualizacao()
        );
    }

    public void updateEntityFromDTO(FornecedorRequestDTO request, Fornecedor fornecedor) {
        fornecedor.setNome(request.nome());
        fornecedor.setTipoDocumento(request.tipoDocumento());
        fornecedor.setDocumento(request.documento());
        fornecedor.setSituacaoCadastral(request.situacaoCadastral());
        fornecedor.setEmail(request.email());
        fornecedor.setTelefone(request.telefone());
        fornecedor.setStreet(request.street());
        fornecedor.setCity(request.city());
        fornecedor.setState(request.state());
        fornecedor.setDataUltimaValidacao(LocalDateTime.now());
    }
}
