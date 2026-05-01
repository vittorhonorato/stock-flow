package com.vittorhonorato.stockflow.exception.estoques;

public class ProdutoInativoParaMovimentacaoException extends RuntimeException {
    public ProdutoInativoParaMovimentacaoException(String message) {
        super(message);
    }
}
