package io.limeup.flexbets.sport.config.stub;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@Profile("mock")
@Slf4j
@Configuration
public class AreaWireMockConfig extends WireMockConfig {

    @Bean
    public CommandLineRunner setupAreaWireMock() {
        return args -> {
            WireMock.configureFor(getWireMockHost(), getWireMockPort());
            WireMock.stubFor(get(urlPathMatching("/v1/areas/list"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withTransformerParameter("countryCodeMapping", Map.of(
                                    "US", "United States",
                                    "EU", "Europe",
                                    "AS", "Asia",
                                    "SA", "South America",
                                    "AF", "Africa"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                            {
                              "count": {{randomInt lower=1 upper=5}},
                              "page": 1,
                              "limit": 50,
                              "total_pages": 1,
                              "areas": [
                                {{#each (range 1 (randomInt lower=1 upper=5))}}
                                {{#assign "areaId"}}{{randomInt lower=10 upper=99}}{{/assign}}
                                {{#assign "countryCode"}}{{pickRandom 'US' 'EU' 'AS' 'SA' 'AF'}}{{/assign}}
                                {{#assign "areaName"}}{{lookup parameters.countryCodeMapping countryCode}}{{/assign}}
                                {
                                  "id": {{areaId}},
                                  "name": "{{#if areaName}}{{areaName}}{{else}}Unknown Area{{/if}}",
                                  "country_code": {{#if countryCode}}"{{countryCode}}"{{else}}null{{/if}}
                                }
                                {{#unless @last}},{{/unless}}
                                {{/each}}
                              ]
                            }
                            """)));
            log.info("WireMock Stub Areas with Randomized Data Initialized!");
        };
    }

}
