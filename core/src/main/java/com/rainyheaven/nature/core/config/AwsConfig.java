package com.rainyheaven.nature.core.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.rainyheaven.nature.core.valuebinder.AwsS3Binder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    private final AwsS3Binder awsS3Binder;

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey(), secretKey());
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    private String accessKey() {
        return awsS3Binder.getS3().get("access-key");
    }

    private String secretKey() {
        return awsS3Binder.getS3().get("secret-key");
    }

}
