package com.vittorhonorato.stockflow.dto.response;

import com.vittorhonorato.stockflow.entity.SituacaoCadastral;
import com.vittorhonorato.stockflow.entity.TipoDocumento;

import java.time.LocalDateTime;

public record ValidacaoDocumentoFornecedorResponseDTO(
        String nome,
        TipoDocumento tipoDocumento,
        String documento,
        SituacaoCadastral situacaoCadastral,
        Boolean documentoValido,
        Boolean permiteCadastro,
        LocalDateTime dataValidacao
) {
}
