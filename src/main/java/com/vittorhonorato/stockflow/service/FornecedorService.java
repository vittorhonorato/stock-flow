package com.vittorhonorato.stockflow.service;

import com.vittorhonorato.stockflow.dto.request.FornecedorRequestDTO;
import com.vittorhonorato.stockflow.dto.response.FornecedorResponseDTO;
import com.vittorhonorato.stockflow.dto.response.ValidacaoDocumentoFornecedorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FornecedorService {
    FornecedorResponseDTO criar(FornecedorRequestDTO request);

    Page<FornecedorResponseDTO> listarTodos(Pageable pageable);

    FornecedorResponseDTO buscarPorId(Long id);

    FornecedorResponseDTO buscarPorDocumento(String documento);

    FornecedorResponseDTO atualizar(Long id, FornecedorRequestDTO request);

    void desativar(Long id);

    ValidacaoDocumentoFornecedorResponseDTO validarDocumento(
            String request
    );

    List<FornecedorResponseDTO> findAllOptions();
}
