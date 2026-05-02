package com.vittorhonorato.stockflow.controller;

import com.vittorhonorato.stockflow.dto.request.ProdutoRequestDTO;
import com.vittorhonorato.stockflow.dto.response.ProdutoResponseDTO;
import com.vittorhonorato.stockflow.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(
            @Valid @RequestBody ProdutoRequestDTO requestDTO
    ) {

        ProdutoResponseDTO response = produtoService.criar(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> findAllProdutos(
            @PageableDefault(size = 5, sort = "id") Pageable pageable
    ) {

        Page<ProdutoResponseDTO> response = produtoService.listAll(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProdutoResponseDTO> findProdutosById(
            @PathVariable Long id
    ) {

        ProdutoResponseDTO response = produtoService.produtoById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("sku/{sku}")
    public ResponseEntity<ProdutoResponseDTO> findProdutosBySku(
            @PathVariable String sku
    ) {

        ProdutoResponseDTO response = produtoService.produtoBySku(sku);

        return ResponseEntity.ok(response);
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO request
    ) {

        ProdutoResponseDTO response = produtoService.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        produtoService.desativar(id);

        return ResponseEntity.noContent().build();
    }
}
