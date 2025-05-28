package io.limeup.flexbets.sport.config.resttemplate;

import io.limeup.flexbets.sport.error.Trade360ApiErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Trade360ApiTemplateConfig {

    @Bean
    public RestTemplate trade360RestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new Trade360ApiErrorHandler());
        return restTemplate;
    }
}
