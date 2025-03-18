package io.limeup.flexbets.sport.config.wiremock.stub;

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
public class AreaWireMockBase extends WireMockBase {

    @Bean
    public CommandLineRunner setupAreaWireMock() {
        return args -> {
            WireMock.configureFor(getWireMockHost(), getWireMockPort());
            WireMock.stubFor(get(urlPathMatching("/v1/areas/list"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withTransformerParameter("countryCodeMapping", Map.of(
                                    "US", "United States",
                                    "EU", "Europe",
                                    "AS", "Asia",
                                    "SA", "South America",
                                    "AF", "Africa"
                            ))
                            .withTransformers("response-template", "custom-pagination-transformer")
                            .withBody("""
                                    {
                                      "count": {{parameters.count}},
                                      "page": {{parameters.page}},
                                      "page_size": {{parameters.page_size}},
                                      "total_pages": {{parameters.total_pages}},
                                      "areas": [
                                        {{#each (range 1 parameters.current_page_count)}}
                                        {{#assign "areaId"}}{{randomInt lower=10 upper=99}}{{/assign}}
                                        {{#assign "countryCode"}}{{pickRandom 'US' 'EU' 'AS' 'SA' 'AF'}}{{/assign}}
                                        {{#assign "areaName"}}{{lookup parameters.countryCodeMapping countryCode}}{{/assign}}
                                        {
                                          "id": {{areaId}},
                                          "name": "{{#if areaName}}{{areaName}}{{else}}Unknown Area{{/if}}",
                                          "country_code": "{{countryCode}}"
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

