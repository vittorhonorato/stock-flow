package com.vittorhonorato.stockflow.exception.produtos;

public class ProdutoSkuDuplicadoException extends RuntimeException {
    public ProdutoSkuDuplicadoException(String message) {
        super(message);
    }
}
