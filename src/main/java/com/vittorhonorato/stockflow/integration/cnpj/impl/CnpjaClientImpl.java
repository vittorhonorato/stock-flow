package com.vittorhonorato.stockflow.integration.cnpj.impl;

import com.vittorhonorato.stockflow.config.CacheNames;
import com.vittorhonorato.stockflow.dto.external.cnpja.CnpjaOfficeResponseDTO;
import com.vittorhonorato.stockflow.exception.integration.CnpjaBadGatewayException;
import com.vittorhonorato.stockflow.exception.integration.CnpjaServiceUnavailableException;
import com.vittorhonorato.stockflow.integration.cnpj.CnpjaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

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
    @Cacheable(cacheNames = CacheNames.CNPJA_CONSULTA, key = "#documento", unless = "#result == null")
    public CnpjaOfficeResponseDTO consultarCnpj(String documento) {
        try {
            return restClient.get()
                    .uri("/office/{cnpj}", documento)
                    .retrieve()
                    .body(CnpjaOfficeResponseDTO.class);
        } catch (ResourceAccessException exception) {
            throw new CnpjaServiceUnavailableException(
                    "Serviço externo de validação de CNPJ indisponível no momento. Tente novamente em instantes."
            );
        } catch (RestClientResponseException exception) {
            throw new CnpjaBadGatewayException(
                    "Falha ao consultar o serviço externo de validação de CNPJ. Tente novamente mais tarde."
            );
        } catch (RestClientException exception) {
            throw new CnpjaBadGatewayException(
                    "Não foi possível processar a resposta do serviço externo de validação de CNPJ."
            );
        }
    }
}
