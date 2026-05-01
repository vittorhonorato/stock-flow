package com.vittorhonorato.stockflow.dto.response;

import com.vittorhonorato.stockflow.entity.TipoMovimentacao;

import java.time.LocalDateTime;

public record HistoricoEstoqueResponseDTO(
        Long id,
        Long produtoId,
        String produtoNome,
        String produtoSku,
        TipoMovimentacao tipoMovimentacao,
        Integer quantidade,
        Integer quantidadeAtualProduto,
        String motivo,
        LocalDateTime dataMovimentacao
) {
}
