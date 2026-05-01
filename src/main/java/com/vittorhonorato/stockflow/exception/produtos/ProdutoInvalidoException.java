package com.vittorhonorato.stockflow.exception.produtos;

public class ProdutoInvalidoException extends RuntimeException {
    public ProdutoInvalidoException(String message) {
        super(message);
    }
}
