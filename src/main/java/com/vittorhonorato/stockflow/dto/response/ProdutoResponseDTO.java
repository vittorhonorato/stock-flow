package com.vittorhonorato.stockflow.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String sku,
        String descricao,
        BigDecimal precoDeCusto,
        BigDecimal precoDeVenda,
        Integer quantidadeAtual,
        Integer quantidadeMinima,
        Boolean ativo,
        Long categoriaId,
        String categoriaNome,
        Long fornecedorId,
        String fornecedorNome,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}
