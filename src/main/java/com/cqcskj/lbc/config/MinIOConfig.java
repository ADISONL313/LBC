package com.cqcskj.lbc.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MinIOConfig {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.root-user}")
    private String rootUser;

    @Value("${minio.root-password}")
    private String rootPassword;
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(rootUser, rootPassword)
                .build();
    }
}
