package io.limeup.flexbets.sport.dto.trade360;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trade360ApiRequestBodyParams {
    private String userName;
    private String password;
    private Integer packageId;
    private List<Integer> sports;
    private List<Integer> locations;
    private List<Integer> leagues;
    private List<Integer> fixtures;
    private List<Integer> markets;
    private Long fromDate;
    private Long toDate;
    private Long timestamp;

    public Trade360ApiRequestBodyParams(String userName, String password, Integer packageId) {
        this.userName = userName;
        this.password = password;
        this.packageId = packageId;
    }
}
