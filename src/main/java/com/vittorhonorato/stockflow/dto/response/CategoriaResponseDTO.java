package com.vittorhonorato.stockflow.dto.response;

import java.time.LocalDateTime;

public record CategoriaResponseDTO(
        Long id,
        String nome,
        String descricao,
        Boolean ativa,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}
