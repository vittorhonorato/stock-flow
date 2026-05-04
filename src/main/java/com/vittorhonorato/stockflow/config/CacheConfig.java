package com.vittorhonorato.stockflow.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setAllowNullValues(false);

        cacheManager.registerCustomCache(
                CacheNames.CNPJA_CONSULTA,
                Caffeine.newBuilder()
                        .maximumSize(2000)
                        .expireAfterWrite(Duration.ofHours(6))
                        .build()
        );

        cacheManager.registerCustomCache(
                CacheNames.CATEGORIAS_OPCOES,
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterWrite(Duration.ofMinutes(15))
                        .build()
        );

        cacheManager.registerCustomCache(
                CacheNames.FORNECEDORES_OPCOES,
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterWrite(Duration.ofMinutes(15))
                        .build()
        );

        return cacheManager;
    }
}
