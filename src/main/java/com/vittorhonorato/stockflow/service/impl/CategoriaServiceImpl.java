package com.vittorhonorato.stockflow.service.impl;

import com.vittorhonorato.stockflow.dto.request.CategoriaRequestDTO;
import com.vittorhonorato.stockflow.dto.response.CategoriaResponseDTO;
import com.vittorhonorato.stockflow.entity.Categoria;
import com.vittorhonorato.stockflow.mapper.CategoriaMapper;
import com.vittorhonorato.stockflow.repository.CategoriaRepository;
import com.vittorhonorato.stockflow.service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Override
    public CategoriaResponseDTO criar(CategoriaRequestDTO categoriaRequestDTO) {
        if (categoriaRepository.existsByNome(categoriaRequestDTO.nome())) {
            throw new RuntimeException("Já existe uma categoria cadastrada com esse nome");
        }

        Categoria categoria = categoriaMapper.toEntity(categoriaRequestDTO);
        categoriaRepository.save(categoria);


        return categoriaMapper.toResponseDTO(categoria);
    }

    @Override
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CategoriaResponseDTO buscaPorId(Long id) {
        Categoria categoria = getCategoria(id);

        return CategoriaMapper.toResponseDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaRequestDTO) {
        return null;
    }

    @Override
    public void desativar(Long id) {
        Categoria categoria = getCategoria(id);

        categoria.setAtiva(false);
        categoriaRepository.save(categoria);
    }

    private Categoria getCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o id:" + id));
    }


}
