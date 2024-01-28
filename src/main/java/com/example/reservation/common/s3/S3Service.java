package com.example.reservation.common.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

//@Service
//public class S3Service {
//
//    @Bean
//    public S3Client s3Client(
//            @Value("${aws.accessKeyId}") String accessKey,
//            @Value("${aws.secretKey}") String secretKey,
//            @Value("${aws.region}") String region
//    ) {
//        return S3Client.builder()
//                .region(Region.of(region))
//                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
//                .build();
//    }
//
//}
