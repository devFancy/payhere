package com.payhere.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<String> allowOriginUrlPatterns;
    private final HandlerMethodArgumentResolver authenticationPrincipalArgumentResolver;

    public WebConfig(@Value("${cors.allow-origin.urls}") final List<String> allowOriginUrlPatterns,
                     final HandlerMethodArgumentResolver authenticationPrincipalArgumentResolver) {
        this.allowOriginUrlPatterns = allowOriginUrlPatterns;
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        String [] patterns = allowOriginUrlPatterns.stream()
                .toArray(String[]::new);

        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(patterns);
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authenticationPrincipalArgumentResolver);
    }
}