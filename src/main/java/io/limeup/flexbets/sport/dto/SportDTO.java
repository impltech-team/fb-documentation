package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportDTO {
    private Integer id;
    private String name;
    private List<Status> statuses;
    private List<ResultType> resultTypes;
    private List<EventDetails> eventDetails;
    private List<TeamStatistic> teamStatistics;
    private List<PlayerStatistic> playerStatistics;
    private List<Incident> incidents;

    @Data
    public static class Status {
        private String statusName;
        private String description;
    }

    @Data
    public static class ResultType {
        private String type;
        private String description;
    }

    @Data
    public static class EventDetails {
        private String name;
        private String description;
    }

    @Data
    public static class TeamStatistic {
        private String statName;
        private String description;
    }

    @Data
    public static class PlayerStatistic {
        private String statName;
        private String description;
    }

    @Data
    public static class Incident {
        private String incidentType;
        private String description;
    }
}
