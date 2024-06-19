package com.hy.newsbackend.common.config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring-docs")
@Setter
@Getter
public class SpringDocsProperties {
    private String title;
    private String description;
    private String version;
    private String header;
    private String scheme;
}

