package com.vittorhonorato.stockflow.controller;

import com.vittorhonorato.stockflow.dto.request.FornecedorRequestDTO;
import com.vittorhonorato.stockflow.dto.response.FornecedorResponseDTO;
import com.vittorhonorato.stockflow.dto.response.ValidacaoDocumentoFornecedorResponseDTO;
import com.vittorhonorato.stockflow.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping("validar-documento/{documento}")
    public ResponseEntity<ValidacaoDocumentoFornecedorResponseDTO> validarDocumento(
            @PathVariable String documento
    ) {

        ValidacaoDocumentoFornecedorResponseDTO response = fornecedorService.validarDocumento(documento);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> getAllFornecedores() {

        List<FornecedorResponseDTO> response = fornecedorService.listarTodos();

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<FornecedorResponseDTO> getFornecedorById(
            @PathVariable Long id
    ) {

        FornecedorResponseDTO response = fornecedorService.buscarPorId(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("busca")
    public ResponseEntity<FornecedorResponseDTO> getFornecedorByDocumento(
            @RequestParam String documento
    ) {

        FornecedorResponseDTO response = fornecedorService.buscarPorDocumento(documento);

        return ResponseEntity.ok(response);
    }

    @PostMapping("criar-fornecedor")
    public ResponseEntity<FornecedorResponseDTO> criarFornecedor(
            @Valid @RequestBody FornecedorRequestDTO fornecedorRequestDTO
            ) {

        FornecedorResponseDTO response = fornecedorService.criar(fornecedorRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("atualizar-fornecedor/{id}")
    public ResponseEntity<FornecedorResponseDTO> atualizaFornecedor(
            @PathVariable Long id,
            @Valid @RequestBody FornecedorRequestDTO request
    ) {

        FornecedorResponseDTO atualizar = fornecedorService.atualizar(id, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(atualizar);
    }


    @PatchMapping("desativar/{id}")
    public ResponseEntity<Void> desativarFornecedor(
            @PathVariable Long id
    ) {

        fornecedorService.desativar(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
