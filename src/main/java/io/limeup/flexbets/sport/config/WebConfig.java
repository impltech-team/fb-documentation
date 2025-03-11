package io.limeup.flexbets.sport.config;

import io.limeup.flexbets.sport.converter.SnakeCaseToCamelCaseArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final SnakeCaseToCamelCaseArgumentResolver argumentResolver;

    public WebConfig(SnakeCaseToCamelCaseArgumentResolver argumentResolver) {
        this.argumentResolver = argumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }
}
