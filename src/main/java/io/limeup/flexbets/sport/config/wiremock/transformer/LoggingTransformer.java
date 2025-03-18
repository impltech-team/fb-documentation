package io.limeup.flexbets.sport.config.wiremock.transformer;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformerV2;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingTransformer implements ResponseDefinitionTransformerV2 {

    @Override
    public ResponseDefinition transform(ServeEvent serveEvent) {
        Request request = serveEvent.getRequest();
        ResponseDefinition response = serveEvent.getResponseDefinition();

        log.info("Incoming Request: {} {}", request.getMethod(), request.getUrl());
        log.info("Headers: {}", request.getHeaders());
        log.info("Outgoing Response: HTTP {}", response.getStatus());

        return ResponseDefinitionBuilder.like(response).build();
    }

    @Override
    public String getName() {
        return "logging-transformer";
    }
}
