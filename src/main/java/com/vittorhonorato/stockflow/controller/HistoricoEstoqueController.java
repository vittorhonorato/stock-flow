package com.vittorhonorato.stockflow.controller;

import com.vittorhonorato.stockflow.dto.request.AjusteEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.request.MovimentacaoEstoqueRequestDTO;
import com.vittorhonorato.stockflow.dto.response.HistoricoEstoqueResponseDTO;
import com.vittorhonorato.stockflow.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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
    public ResponseEntity<Page<HistoricoEstoqueResponseDTO>> listAllHistorico(
            @PathVariable Long produtoId,
            Pageable pageable
    ) {

        Page<HistoricoEstoqueResponseDTO> response =
                estoqueService.listaHistoricoPorProduto(produtoId, pageable);

        return ResponseEntity.ok(response);
    }
}
