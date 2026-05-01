package com.vittorhonorato.stockflow.service;

import com.vittorhonorato.stockflow.dto.request.AjusteEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.request.MovimentacaoEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.response.HistoricoEstoqueResponseDTO;

import java.util.List;

public interface EstoqueService {
    HistoricoEstoqueResponseDTO entrada(MovimentacaoEstoqueRequestDTO request);
    HistoricoEstoqueResponseDTO saida(MovimentacaoEstoqueRequestDTO request);
    HistoricoEstoqueResponseDTO ajuste(AjusteEstoqueRequestDTO request);
    List<HistoricoEstoqueResponseDTO> listaHistoricoPorProduto(Long produtoId);
}
