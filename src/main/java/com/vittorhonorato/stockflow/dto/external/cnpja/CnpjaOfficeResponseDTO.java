package com.vittorhonorato.stockflow.dto.external.cnpja;

import java.util.List;

public record CnpjaOfficeResponseDTO(
        String taxId,
        CnpjaCompanyDTO company,
        CnpjaStatusDTO status,
        CnpjaAddressDTO address,
        List<CnpjaEmailDTO> emails,
        List<CnpjaPhoneDTO> phones
) {
}
