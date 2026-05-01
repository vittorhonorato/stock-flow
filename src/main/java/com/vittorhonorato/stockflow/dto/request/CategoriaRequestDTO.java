package com.vittorhonorato.stockflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(

        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
        @Pattern(
                regexp = ".*[A-Za-zÀ-ÿ].*",
                message = "O nome da categoria deve conter pelo menos uma letra"
        )
        String nome,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao
) {
}
