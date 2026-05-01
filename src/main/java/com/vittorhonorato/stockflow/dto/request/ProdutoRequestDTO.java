package com.vittorhonorato.stockflow.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequestDTO(

        @NotBlank(message = "O nome do produto é obrigatório")
        @Size(max = 160, message = "O nome deve ter no máximo 160 caracteres")
        String nome,

        @NotBlank(message = "O SKU do produto é obrigatório")
        @Size(max = 80, message = "O SKU deve ter no máximo 80 caracteres")
        String sku,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao,

        @NotNull(message = "O preço de custo é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço de custo deve ser maior que zero")
        BigDecimal precoDeCusto,

        @NotNull(message = "O preço de venda é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço de venda deve ser maior que zero")
        BigDecimal precoDeVenda,

        @NotNull(message = "A quantidade mínima é obrigatória")
        @PositiveOrZero(message = "A quantidade mínima não pode ser negativa")
        Integer quantidadeMinima,

        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId,

        @NotNull(message = "O fornecedor é obrigatório")
        Long fornecedorId
) {
}
