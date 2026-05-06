package com.vittorhonorato.stockflow.exception.integration;

public class S3ServiceUnavailableException extends RuntimeException {
    public S3ServiceUnavailableException(String message) {
        super(message);
    }
}
