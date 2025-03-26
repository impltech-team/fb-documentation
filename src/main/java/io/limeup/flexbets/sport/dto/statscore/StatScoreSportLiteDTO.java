package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreSportLiteDTO {
    private Integer id;
    private Integer lsId;
    private String name;
    private String url;
    private String active;
    private String hasTimer;
}
