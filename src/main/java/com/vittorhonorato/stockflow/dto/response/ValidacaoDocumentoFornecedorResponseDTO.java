package com.vittorhonorato.stockflow.dto.response;

import com.vittorhonorato.stockflow.entity.SituacaoCadastral;
import com.vittorhonorato.stockflow.entity.TipoDocumento;

import java.time.LocalDateTime;

public record ValidacaoDocumentoFornecedorResponseDTO(
        String nome,
        TipoDocumento tipoDocumento,
        String documento,
        String email,
        String telefone,
        String street,
        String city,
        String state,
        SituacaoCadastral situacaoCadastral,
        Boolean documentoValido,
        Boolean permiteCadastro,
        LocalDateTime dataValidacao
) {
}
