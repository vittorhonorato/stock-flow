package com.vittorhonorato.stockflow.exception.integration;

public class S3BadGatewayException extends RuntimeException {
    public S3BadGatewayException(String message) {
        super(message);
    }
}
