package com.vittorhonorato.stockflow.controller;

import com.vittorhonorato.stockflow.dto.request.CategoriaRequestDTO;
import com.vittorhonorato.stockflow.dto.response.CategoriaResponseDTO;
import com.vittorhonorato.stockflow.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(
            @Valid @RequestBody CategoriaRequestDTO request
    ) {
        CategoriaResponseDTO response = categoriaService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        List<CategoriaResponseDTO> response = categoriaService.listarTodas();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {
        CategoriaResponseDTO response = categoriaService.buscaPorId(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO request
    ) {
        CategoriaResponseDTO response = categoriaService.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(
            @PathVariable Long id
    ) {
        categoriaService.desativar(id);

        return ResponseEntity.noContent().build();
    }
}
