package com.vittorhonorato.stockflow.service;

import com.vittorhonorato.stockflow.dto.request.ProdutoRequestDTO;
import com.vittorhonorato.stockflow.dto.response.ProdutoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProdutoService {
    ProdutoResponseDTO criar(ProdutoRequestDTO requestDTO);
    Page<ProdutoResponseDTO> listAll(Pageable pageable);
    ProdutoResponseDTO produtoById(Long id);
    ProdutoResponseDTO produtoBySku(String sku);
    ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO requestDTO);
    void desativar(Long id);
}
