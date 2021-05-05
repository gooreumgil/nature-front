package com.rainyheaven.nature.core.valuebinder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "custom")
@Getter
@Setter
public class CustomValueBinder {

    private String uploadPath;

}
