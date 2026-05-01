package com.vittorhonorato.stockflow.service;

import com.vittorhonorato.stockflow.dto.request.ProdutoRequestDTO;
import com.vittorhonorato.stockflow.dto.response.ProdutoResponseDTO;

import java.util.List;

public interface ProdutoService {
    ProdutoResponseDTO criar(ProdutoRequestDTO requestDTO);
    List<ProdutoResponseDTO> listAll();
    ProdutoResponseDTO produtoById(Long id);
    ProdutoResponseDTO produtoBySku(String sku);
    ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO requestDTO);
    void desativar(Long id);
}
