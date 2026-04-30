package com.vittorhonorato.stockflow.service;

import com.vittorhonorato.stockflow.dto.request.CategoriaRequestDTO;
import com.vittorhonorato.stockflow.dto.response.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaResponseDTO criar(CategoriaRequestDTO categoriaRequestDTO);
    List<CategoriaResponseDTO> listarTodas();
    CategoriaResponseDTO buscaPorId(Long id);
    CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaRequestDTO);
    void desativar(Long id);
}
