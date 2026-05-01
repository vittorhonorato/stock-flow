package com.vittorhonorato.stockflow.dto.response;

import com.vittorhonorato.stockflow.entity.SituacaoCadastral;
import com.vittorhonorato.stockflow.entity.TipoDocumento;

import java.time.LocalDateTime;

public record FornecedorResponseDTO(
        Long id,
        String nome,
        TipoDocumento tipoDocumento,
        String documento,
        SituacaoCadastral situacaoCadastral,
        String email,
        String telefone,
        Boolean ativo,
        LocalDateTime dataUltimaValidacao,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}