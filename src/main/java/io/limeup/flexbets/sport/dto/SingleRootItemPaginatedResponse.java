package io.limeup.flexbets.sport.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleRootItemPaginatedResponse<T> {
    private int count;
    private int page;
    private int pageSize;
    private int totalPages;
    @JsonIgnore
    private String rootName;

    @JsonIgnore
    private T data;

    @JsonAnyGetter
    public Map<String, Object> dynamicRoot() {
        return Map.of(rootName, data);
    }
}
