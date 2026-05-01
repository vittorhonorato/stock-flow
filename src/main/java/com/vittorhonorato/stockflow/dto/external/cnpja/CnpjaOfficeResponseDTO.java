package com.vittorhonorato.stockflow.dto.external.cnpja;

public record CnpjaOfficeResponseDTO(
        String taxId,
        CnpjaCompanyDTO company,
        CnpjaStatusDTO status
) {
}
