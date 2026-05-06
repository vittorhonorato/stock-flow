package com.vittorhonorato.stockflow.service;

import com.vittorhonorato.stockflow.dto.request.ProdutoRequestDTO;
import com.vittorhonorato.stockflow.dto.response.ProdutoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface ProdutoService {
    ProdutoResponseDTO criar(ProdutoRequestDTO requestDTO);
    ProdutoResponseDTO criar(ProdutoRequestDTO requestDTO, MultipartFile imagem);
    Page<ProdutoResponseDTO> listAll(Pageable pageable);
    ProdutoResponseDTO produtoById(Long id);
    ProdutoResponseDTO produtoBySku(String sku);
    ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO requestDTO);
    ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO requestDTO, MultipartFile imagem);
    void desativar(Long id);
}
