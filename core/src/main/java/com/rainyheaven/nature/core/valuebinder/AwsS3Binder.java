package com.rainyheaven.nature.core.valuebinder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.HashMap;
import java.util.Map;

@ConstructorBinding
@ConfigurationProperties(prefix = "aws")
@Getter
@Setter
public class AwsS3Binder {

    private Map<String, String> s3 = new HashMap<>();

}
