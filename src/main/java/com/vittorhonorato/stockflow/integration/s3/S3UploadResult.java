package com.vittorhonorato.stockflow.integration.s3;

public record S3UploadResult(
        String objectKey,
        String objectUrl
) {
}
