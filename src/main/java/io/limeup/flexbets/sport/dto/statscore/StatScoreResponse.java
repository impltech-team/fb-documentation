package io.limeup.flexbets.sport.dto.statscore;

import lombok.Data;

import java.util.Map;

@Data
public class StatScoreResponse<T> {

    private ApiWrapper<T> api;

    @Data
    public static class ApiWrapper<T> {
        private MethodInfo method;
        private T data;
    }

    @Data
    public static class MethodInfo {
        private String name;
        private String details;
        private Map<String, Object> parameters;
        private Integer total_items;
        private String previous_page;
        private String next_page;
    }
}
