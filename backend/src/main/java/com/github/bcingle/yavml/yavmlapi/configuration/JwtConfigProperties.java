package com.github.bcingle.yavml.yavmlapi.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Configuration
@ConfigurationProperties("yavml.api.jwt")
@Data
public class JwtConfigProperties {

    /**
     * Must be specified at runtime to prevent accidentally using a default secret key
     */
    @NotNull
    @Size(min = 32)
    private String secretKey;

    private long tokenDuration = 1000 * 60 * 60; // 1 hour in milliseconds

    private String tokenHeaderKey = "Authorization";

    private String tokenHeaderValuePrefix = "Bearer ";

}
