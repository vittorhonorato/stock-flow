package com.vittorhonorato.stockflow.service;

import com.vittorhonorato.stockflow.dto.request.FornecedorRequestDTO;
import com.vittorhonorato.stockflow.dto.request.ValidarDocumentoFornecedorRequestDTO;
import com.vittorhonorato.stockflow.dto.response.FornecedorResponseDTO;
import com.vittorhonorato.stockflow.dto.response.ValidacaoDocumentoFornecedorResponseDTO;


import java.util.List;

public interface FornecedorService {
    FornecedorResponseDTO criar(FornecedorRequestDTO request);

    List<FornecedorResponseDTO> listarTodos();

    FornecedorResponseDTO buscarPorId(Long id);

    FornecedorResponseDTO buscarPorDocumento(String documento);

    FornecedorResponseDTO atualizar(Long id, FornecedorRequestDTO request);

    void desativar(Long id);

    ValidacaoDocumentoFornecedorResponseDTO validarDocumento(
            String request
    );
}
