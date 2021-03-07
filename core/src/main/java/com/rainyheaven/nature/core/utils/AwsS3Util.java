package com.rainyheaven.nature.core.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.rainyheaven.nature.core.valuebinder.AwsS3Binder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AwsS3Util {

    private final AmazonS3 amazonS3;
    private final AwsS3Binder awsProperties;

    public PutObjectResult upload(String key, File file) throws IOException {
        return amazonS3.putObject(new PutObjectRequest(bucketName(), key, file).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void delete(String key) {
        amazonS3.deleteObject(bucketName(), key);
    }

    private String bucketName() {
        return awsProperties.getS3().get("bucket-name");
    }

    public String generateS3Key(String folderName, String fileName, UUID uuid) {
        return folderName + "/" + uuid + "." + FilenameUtils.getExtension(fileName);
    }

}

