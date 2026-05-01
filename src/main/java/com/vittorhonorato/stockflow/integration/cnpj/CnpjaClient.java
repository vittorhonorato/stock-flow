package com.vittorhonorato.stockflow.integration.cnpj;

import com.vittorhonorato.stockflow.dto.external.cnpja.CnpjaOfficeResponseDTO;

public interface CnpjaClient {
    CnpjaOfficeResponseDTO consultarCnpj(String documento);
}
