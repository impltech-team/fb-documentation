package io.limeup.flexbets.sport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FullEventDTO {
    private int id;
    private String competition;
    private int competitionId;
    private String eventName;
    private String eventDate;
    private String status;
    private List<Participant> participants;
    private Venue venue;
    private List<Incident> incidents;
    private List<Market> markets;

    @Data
    @NoArgsConstructor
    public static class Participant {
        private int participantId;
        private String participantName;
        private String acronym;
        private boolean isHome;
        private Integer score;
        private List<Lineup> lineups;
        private List<Stat> stats;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Lineup {
            private int subParticipantId;
            private String subParticipantName;
            private String position;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Stat {
            private String statName;
            private String value;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Venue {
        private int venueId;
        private String venueName;
        private String location;
        private Integer capacity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Incident {
        private String time;
        private int participantId;
        private String participantName;
        private int subParticipantId;
        private String subParticipantName;
        private String info;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Market {
        private int id;
        private String name;
        private String type;
        private List<Bet> bets;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bet {
        private long id;
        private Integer participantId;
        private String participantName;
        private String type;
        private String price;
    }
}
