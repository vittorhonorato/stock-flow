package com.vittorhonorato.stockflow.controller;

import com.vittorhonorato.stockflow.dto.request.AjusteEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.request.MovimentacaoEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.response.HistoricoEstoqueResponseDTO;
import com.vittorhonorato.stockflow.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("estoque")
public class HistoricoEstoqueController {

    private final EstoqueService estoqueService;

    public HistoricoEstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping("entrada")
    public ResponseEntity<HistoricoEstoqueResponseDTO> entrada(
            @Valid @RequestBody MovimentacaoEstoqueRequestDTO request
            ) {

        HistoricoEstoqueResponseDTO response = estoqueService.entrada(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("saida")
    public ResponseEntity<HistoricoEstoqueResponseDTO> saida(
            @Valid @RequestBody MovimentacaoEstoqueRequestDTO request
    ) {

        HistoricoEstoqueResponseDTO response = estoqueService.saida(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("ajuste")
    public ResponseEntity<HistoricoEstoqueResponseDTO> ajuste(
            @Valid @RequestBody AjusteEstoqueRequestDTO request
    ) {

        HistoricoEstoqueResponseDTO response = estoqueService.ajuste(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/produtos/{produtoId}/historico")
    public ResponseEntity<List<HistoricoEstoqueResponseDTO>> listAllHistorico(
            @PathVariable Long produtoId
    ) {

        List<HistoricoEstoqueResponseDTO> response =
                estoqueService.listaHistoricoPorProduto(produtoId);

        return ResponseEntity.ok(response);
    }
}
