package io.limeup.flexbets.sport.dto.statscore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
    private Api api;

    @Data
    public static class Api {
        private AuthData data;
    }

    @Data
    public static class AuthData {
        @JsonProperty("client_id")
        private String clientId;

        private String token;

        @JsonProperty("token_expiration")
        private long tokenExpiration;
    }
}
