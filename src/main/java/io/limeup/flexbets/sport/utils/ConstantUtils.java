package io.limeup.flexbets.sport.utils;

public class ConstantUtils {
    private ConstantUtils() { }

    public static final class Common {
        private Common() { }
        public static final String STATUS = "status";
        public static final String ERROR = "error";
        public static final String MESSAGE = "message";
        public static final String PATH = "path";
    }

    public static final class Mock {
        private Mock() { }
        public static final String ID = "id";
        public static final String PARTICIPANTS = "participants";
        public static final String SUB_PARTICIPANTS = "subparticipants";
        public static final String TEAM_FORMATION = "team_formation";
        public static final String FORMATION = "formation";
        public static final String MARKETS = "markets";
        public static final String PRICE = "price";
        public static final String STAT_VALUE = "stat_value";
        public static final String MARKET_IDS = "market_ids";
        public static final String MARKET_ID = "market_id";
        public static final String SUB_PARTICIPANT_ID = "sub_participant_id";
    }

    public static final class StatScoreWebClient {
        private StatScoreWebClient() { }
        public static final String COMPETITION = "competition";
        public static final String COMPETITIONS = "competitions";
        public static final String TIMESTAMP = "timestamp";
        public static final String SORT_TYPE = "sort_type";
        public static final String PARTICIPANT_ID = "participant_id";
        public static final String SUB_PARTICIPANT_ID = "sub_participant_id";
        public static final String STAGE_ID = "stage_id";
        public static final String SEASON_ID = "season_id";
        public static final String SPORT_ID = "sport_id";
        public static final String AREA_ID = "area_id";
        public static final String COMPETITION_ID = "competition_id";
        public static final String EVENT_ID = "event_id";
        public static final String MULTI_IDS = "multi_ids";
        public static final String MARKET_IDS = "market_ids";
        public static final String PARTICIPANT = "participant";
        public static final String PARTICIPANTS = "participants";
        public static final String SUB_PARTICIPANTS = "sub_participants";
        public static final String LIMIT = "limit";
        public static final String PAGE = "page";
        public static final String LANG = "lang";
    }

    public static final class Batch {
        private Batch() {
        }
        public static final String PRE_FETCH_STAT_SCORE_DATA_JOB = "prefetchJob";
    }

    public static final class TestConstants {
        private TestConstants() {
        }

        public static final String FOOTBALL = "Football";
        public static final String PLAYER_ASSISTS_MARKET = "Under/Over Player Assists";
        public static final String ASSISTS = "Assists";
        public static final String REBOUNDS = "Rebounds";
        public static final String FORWARD = "Forward";
        public static final String USA = "USA";
    }
}
