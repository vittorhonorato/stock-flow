package com.vittorhonorato.stockflow.integration.s3;

import org.springframework.web.multipart.MultipartFile;

public interface S3StorageClient {

    S3UploadResult uploadProdutoImagem(MultipartFile file, String skuNormalizado);

    void deleteObject(String objectKey);
}
