package com.rainyheaven.nature.core.domain.reviewimage;

import com.rainyheaven.nature.core.utils.AwsS3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewImageService {

    private final AwsS3Util awsS3Util;

    public ReviewImage uploadImage(File file, UUID uuid) {
        String key = awsS3Util.generateS3Key("review-srcs", file.getName(), uuid);
        try {
            awsS3Util.upload(key, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ReviewImage.create(key);
    }
}
