package com.vittorhonorato.stockflow.entity;

import java.util.Arrays;

public enum SituacaoCadastral {
    NULA(1, "Nula"),
    ATIVA(2, "Ativa"),
    SUSPENSA(3, "Suspensa"),
    INAPTA(4, "Inapta"),
    BAIXADA(8, "Baixada");

    private final Integer codigo;
    private final String descricao;

    SituacaoCadastral(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SituacaoCadastral fromCodigo(Integer codigo) {
        return Arrays.stream(SituacaoCadastral.values())
                .filter(situacao -> situacao.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Código de situação cadastral inválido: " + codigo
                ));
    }
}
