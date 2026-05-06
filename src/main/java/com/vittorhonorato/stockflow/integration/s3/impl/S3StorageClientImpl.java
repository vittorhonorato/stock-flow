package com.vittorhonorato.stockflow.integration.s3.impl;

import com.vittorhonorato.stockflow.config.S3Properties;
import com.vittorhonorato.stockflow.exception.integration.S3BadGatewayException;
import com.vittorhonorato.stockflow.exception.integration.S3ServiceUnavailableException;
import com.vittorhonorato.stockflow.exception.produtos.ProdutoInvalidoException;
import com.vittorhonorato.stockflow.integration.s3.S3StorageClient;
import com.vittorhonorato.stockflow.integration.s3.S3UploadResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

@Component
public class S3StorageClientImpl implements S3StorageClient {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final int MIN_CONTENT_TYPE_LENGTH = 6;

    private final S3Client s3Client;
    private final S3Properties properties;

    public S3StorageClientImpl(S3Client s3Client, S3Properties properties) {
        this.s3Client = s3Client;
        this.properties = properties;
    }

    @Override
    public S3UploadResult uploadProdutoImagem(MultipartFile file, String skuNormalizado) {
        validarArquivo(file);

        String objectKey = gerarObjectKey(file, skuNormalizado);
        String contentType = file.getContentType();

        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(properties.getBucket())
                    .key(objectKey)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(inputStream, file.getSize()));

            return new S3UploadResult(objectKey, construirUrlPublica(objectKey));
        } catch (IOException exception) {
            throw new ProdutoInvalidoException("Não foi possível ler o arquivo da imagem enviado");
        } catch (SdkClientException exception) {
            throw new S3ServiceUnavailableException(
                    "Serviço de armazenamento de imagens indisponível no momento. Tente novamente em instantes."
            );
        } catch (S3Exception exception) {
            throw new S3BadGatewayException(
                    "Falha ao persistir a imagem do produto no serviço de armazenamento."
            );
        }
    }

    @Override
    public void deleteObject(String objectKey) {
        if (!StringUtils.hasText(objectKey)) {
            return;
        }

        try {
            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(properties.getBucket())
                            .key(objectKey)
                            .build()
            );
        } catch (SdkClientException | S3Exception ignored) {
        }
    }

    private void validarArquivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ProdutoInvalidoException("A imagem do produto é obrigatória para este fluxo");
        }

        String contentType = file.getContentType();
        if (!StringUtils.hasText(contentType) || contentType.length() < MIN_CONTENT_TYPE_LENGTH) {
            throw new ProdutoInvalidoException("Tipo de arquivo da imagem é inválido");
        }

        String normalizedContentType = contentType.toLowerCase(Locale.ROOT);
        if (!normalizedContentType.startsWith("image/")) {
            throw new ProdutoInvalidoException("Arquivo inválido. Envie somente imagens");
        }
    }

    private String gerarObjectKey(MultipartFile file, String skuNormalizado) {
        String extension = getFileExtension(file.getOriginalFilename());
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String randomToken = UUID.randomUUID().toString().substring(0, 8);
        String skuSegment = sanitizeSegment(skuNormalizado);

        return "produtos/" + skuSegment + "/" + timestamp + "-" + randomToken + extension;
    }

    private String sanitizeSegment(String value) {
        if (!StringUtils.hasText(value)) {
            return "sem-sku";
        }

        String sanitized = value.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9-_]", "-");

        if (!StringUtils.hasText(sanitized)) {
            return "sem-sku";
        }

        return sanitized;
    }

    private String getFileExtension(String fileName) {
        if (!StringUtils.hasText(fileName) || !fileName.contains(".")) {
            return ".bin";
        }

        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase(Locale.ROOT);

        if (extension.length() > 10) {
            return ".bin";
        }

        return extension;
    }

    private String construirUrlPublica(String objectKey) {
        String baseUrl = StringUtils.hasText(properties.getPublicBaseUrl())
                ? properties.getPublicBaseUrl()
                : properties.getEndpoint();

        if (!StringUtils.hasText(baseUrl)) {
            throw new S3BadGatewayException("URL base do serviço de armazenamento não configurada");
        }

        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;

        return normalizedBaseUrl + "/" + properties.getBucket() + "/" + objectKey;
    }
}
