package com.vittorhonorato.stockflow.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class S3Config {

    @Bean
    public S3Client s3Client(S3Properties properties) {
        var builder = S3Client.builder()
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())
                        )
                )
                .region(Region.of(properties.getRegion()))
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(properties.isPathStyleAccess())
                                .build()
                );

        if (StringUtils.hasText(properties.getEndpoint())) {
            builder.endpointOverride(URI.create(properties.getEndpoint()));
        }

        return builder.build();
    }

    @Bean
    public ApplicationRunner ensureS3Bucket(S3Client s3Client, S3Properties properties) {
        return args -> {
            if (!properties.isAutoCreateBucket()) {
                return;
            }

            try {
                s3Client.headBucket(
                        HeadBucketRequest.builder()
                                .bucket(properties.getBucket())
                                .build()
                );
            } catch (NoSuchBucketException exception) {
                createBucket(s3Client, properties.getBucket());
            } catch (S3Exception exception) {
                if (exception.statusCode() == 404) {
                    createBucket(s3Client, properties.getBucket());
                    return;
                }

                throw exception;
            }
        };
    }

    private void createBucket(S3Client s3Client, String bucket) {
        s3Client.createBucket(
                CreateBucketRequest.builder()
                        .bucket(bucket)
                        .build()
        );
    }
}
