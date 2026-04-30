package com.vittorhonorato.stockflow.mapper;

import com.vittorhonorato.stockflow.dto.request.CategoriaRequestDTO;
import com.vittorhonorato.stockflow.dto.response.CategoriaResponseDTO;
import com.vittorhonorato.stockflow.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequestDTO request) {
        Categoria categoria = new Categoria();
        categoria.setNome(request.nome());
        categoria.setDescricao(request.descricao());
        categoria.setAtiva(true);

        return categoria;
    }

    public static CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao(),
                categoria.getAtiva(),
                categoria.getDataCriacao(),
                categoria.getDataAtualizacao()
        );
    }

    public void updateEntityFromDTO(CategoriaRequestDTO request, Categoria categoria) {
        categoria.setNome(request.nome());
        categoria.setDescricao(request.descricao());
    }
}
