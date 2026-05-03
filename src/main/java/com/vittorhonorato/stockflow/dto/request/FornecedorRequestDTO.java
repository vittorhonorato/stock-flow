package com.vittorhonorato.stockflow.dto.request;

import com.vittorhonorato.stockflow.entity.SituacaoCadastral;
import com.vittorhonorato.stockflow.entity.TipoDocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FornecedorRequestDTO(

        @NotBlank(message = "O nome do fornecedor é obrigatório")
        @Size(max = 160, message = "O nome deve ter no máximo 160 caracteres")
        String nome,

        @NotNull(message = "O tipo de documento é obrigatório")
        TipoDocumento tipoDocumento,

        @NotBlank(message = "O documento é obrigatório")
        @Size(max = 20, message = "O documento deve ter no máximo 20 caracteres")
        String documento,

        @NotNull(message = "A situação cadastral é obrigatória")
        SituacaoCadastral situacaoCadastral,

        @Email(message = "E-mail inválido")
        @Size(max = 160, message = "O e-mail deve ter no máximo 160 caracteres")
        String email,

        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
        String telefone,

        @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
        String street,

        @Size(max = 120, message = "A cidade deve ter no máximo 120 caracteres")
        String city,

        @Size(max = 2, message = "A UF deve ter no máximo 2 caracteres")
        String state
) {
}
