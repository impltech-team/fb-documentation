package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreBracketDTO {
    @EqualsAndHashCode.Include
    private Integer id;
    private Integer stageId;
    private Integer parentId;
    private Integer seriesId;
    private Integer roundId;
    private String roundName;
    private Integer eventId;
    private String startDate;
    private Integer bye;
    private List<Participant> participants;

    @Data
    public static class Participant {
        private Integer id;
        private String name;
        private String shortName;
        private List<Result> results;
    }

    @Data
    public static class Result {
        private Integer id;
        private String name;
        private String value;
    }
}
