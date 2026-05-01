package com.vittorhonorato.stockflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ValidarDocumentoFornecedorRequestDTO(

        @NotBlank(message = "O documento é obrigatório")
        @Size(max = 20, message = "O documento deve ter no máximo 20 caracteres")
        String documento
) {
}
