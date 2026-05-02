package com.vittorhonorato.stockflow.service;

import com.vittorhonorato.stockflow.dto.request.AjusteEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.request.MovimentacaoEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.response.HistoricoEstoqueResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EstoqueService {
    HistoricoEstoqueResponseDTO entrada(MovimentacaoEstoqueRequestDTO request);
    HistoricoEstoqueResponseDTO saida(MovimentacaoEstoqueRequestDTO request);
    HistoricoEstoqueResponseDTO ajuste(AjusteEstoqueRequestDTO request);
    Page<HistoricoEstoqueResponseDTO> listaHistoricoPorProduto(Long produtoId, Pageable pageable);
}
