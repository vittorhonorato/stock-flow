package com.vittorhonorato.stockflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record AjusteEstoqueRequestDTO(

        @NotNull(message = "O produto é obrigatório")
        Long produtoId,

        @NotNull(message = "A nova quantidade é obrigatória")
        @PositiveOrZero(message = "A nova quantidade não pode ser negativa")
        Integer novaQuantidade,

        @NotBlank(message = "O motivo do ajuste é obrigatório")
        @Size(max = 255, message = "O motivo deve ter no máximo 255 caracteres")
        String motivo
) {
}
