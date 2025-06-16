package io.limeup.flexbets.sport.config.webclient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "sportsdata")
public class SportsDataProps {
    private String baseUrl;
    private String key;
}
