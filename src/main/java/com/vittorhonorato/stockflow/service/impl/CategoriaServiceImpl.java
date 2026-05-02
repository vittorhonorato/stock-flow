package com.vittorhonorato.stockflow.service.impl;

import com.vittorhonorato.stockflow.dto.request.CategoriaRequestDTO;
import com.vittorhonorato.stockflow.dto.response.CategoriaResponseDTO;
import com.vittorhonorato.stockflow.entity.Categoria;
import com.vittorhonorato.stockflow.exception.categorias.CategoriaDuplicadaException;
import com.vittorhonorato.stockflow.exception.categorias.CategoriaNaoEncontradaException;
import com.vittorhonorato.stockflow.mapper.CategoriaMapper;
import com.vittorhonorato.stockflow.repository.CategoriaRepository;
import com.vittorhonorato.stockflow.service.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        String nomeNormalizado = normalizarNome(categoriaRequestDTO.nome());

        if (categoriaRepository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new CategoriaDuplicadaException("Já existe uma categoria cadastrada com esse nome");
        }

        Categoria categoria = categoriaMapper.toEntity(categoriaRequestDTO);
        categoria.setNome(nomeNormalizado);

        Categoria categoriaSave = categoriaRepository.save(categoria);


        return categoriaMapper.toResponseDTO(categoriaSave);
    }

    @Override
    public Page<CategoriaResponseDTO> listarTodas(Pageable pageable) {
        return categoriaRepository.findAll(pageable)
                .map(categoriaMapper::toResponseDTO);
    }

    @Override
    public List<CategoriaResponseDTO> findAllOptions() {
        return categoriaRepository.findByAtivaTrueOrderByNomeAsc()
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CategoriaResponseDTO buscaPorId(Long id) {
        Categoria categoria = getCategoria(id);

        return categoriaMapper.toResponseDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = getCategoria(id);

        String nomeNormalizado = normalizarNome(categoriaRequestDTO.nome());

        boolean nomeFoiAlterado = !categoria.getNome().equalsIgnoreCase(nomeNormalizado);

        if (nomeFoiAlterado && categoriaRepository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new CategoriaDuplicadaException(
                    "Já existe uma categoria cadastrada com esse nome"
            );
        }

        categoriaMapper.updateEntityFromDTO(categoriaRequestDTO, categoria);
        categoria.setNome(nomeNormalizado);

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);

        return categoriaMapper.toResponseDTO(categoriaAtualizada);
    }

    @Override
    public void desativar(Long id) {
        Categoria categoria = getCategoria(id);

        categoria.setAtiva(false);
        categoriaRepository.save(categoria);
    }

    private Categoria getCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com o id:" + id));
    }

    private String normalizarNome(String nome) {
        return nome.trim();
    }
}
