package com.vittorhonorato.stockflow.entity;

import com.vittorhonorato.stockflow.exception.fornecedores.SituacaoCadastralFornecedorInvalidaException;

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
        if (codigo == null) {
            throw new SituacaoCadastralFornecedorInvalidaException(
                    "Código da situação cadastral não pode ser nulo"
            );
        }

        return Arrays.stream(values())
                .filter(situacao -> situacao.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() -> new SituacaoCadastralFornecedorInvalidaException(
                        "Código de situação cadastral inválido: " + codigo
                ));
    }
}
