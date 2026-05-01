package com.vittorhonorato.stockflow.mapper;

import com.vittorhonorato.stockflow.dto.response.HistoricoEstoqueResponseDTO;
import com.vittorhonorato.stockflow.entity.HistoricoEstoque;
import org.springframework.stereotype.Component;

@Component
public class HistoricoEstoqueMapper {

    public HistoricoEstoqueResponseDTO toResponseDTO(HistoricoEstoque historicoEstoque) {
        return new HistoricoEstoqueResponseDTO(
                historicoEstoque.getId(),
                historicoEstoque.getProduto().getId(),
                historicoEstoque.getProduto().getNome(),
                historicoEstoque.getProduto().getSku(),
                historicoEstoque.getTipoMovimentacao(),
                historicoEstoque.getQuantidade(),
                historicoEstoque.getProduto().getQuantidadeAtual(),
                historicoEstoque.getMotivo(),
                historicoEstoque.getDataMovimentacao()
        );
    }
}
