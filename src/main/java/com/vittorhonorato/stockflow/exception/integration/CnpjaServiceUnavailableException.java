package com.vittorhonorato.stockflow.exception.integration;

public class CnpjaServiceUnavailableException extends RuntimeException {

    public CnpjaServiceUnavailableException(String message) {
        super(message);
    }
}
