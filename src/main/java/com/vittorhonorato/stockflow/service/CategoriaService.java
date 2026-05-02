package com.vittorhonorato.stockflow.service;

import com.vittorhonorato.stockflow.dto.request.CategoriaRequestDTO;
import com.vittorhonorato.stockflow.dto.response.CategoriaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriaService {
    CategoriaResponseDTO criar(CategoriaRequestDTO categoriaRequestDTO);
    Page<CategoriaResponseDTO> listarTodas(Pageable pageable);
    List<CategoriaResponseDTO> findAllOptions();
    CategoriaResponseDTO buscaPorId(Long id);
    CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaRequestDTO);
    void desativar(Long id);
}
