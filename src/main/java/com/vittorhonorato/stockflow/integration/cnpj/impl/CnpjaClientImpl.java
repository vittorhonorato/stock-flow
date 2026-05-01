package com.vittorhonorato.stockflow.integration.cnpj.impl;

import com.vittorhonorato.stockflow.dto.external.cnpja.CnpjaOfficeResponseDTO;
import com.vittorhonorato.stockflow.integration.cnpj.CnpjaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CnpjaClientImpl implements CnpjaClient {

    private final RestClient restClient;

    public CnpjaClientImpl(
            RestClient.Builder builder,
            @Value("${app.integrations.cnpja.base-url}") String baseUrl
    ) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .defaultHeader("User-Agent", "stockflow-api")
                .build();
    }

    @Override
    public CnpjaOfficeResponseDTO consultarCnpj(String documento) {

        return restClient.get()
                .uri("/office/{cnpj}", documento)
                .retrieve()
                .body(CnpjaOfficeResponseDTO.class);

    }
}
