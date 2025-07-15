package io.limeup.flexbets.sport.constants;

import java.util.List;
import java.util.stream.Stream;

public class NflMarketTypes {

    // Player-specific markets
    public static final List<String> PLAYER_MARKETS = List.of(
            "Player Prop",
            "Touchdown Scorer",
            "Passing Yards",
            "Rushing Yards",
            "Receiving Yards",
            "Anytime TD",
            "Receptions",
            "Passing TDs",
            "Interceptions"
    );

    // Team-specific markets
    public static final List<String> TEAM_MARKETS = List.of(
            "Game Line",
            "Team Total Points",
            "Spread",
            "Moneyline",
            "Total Points",
            "First Half Spread"
    );

    // Special markets
    public static final List<String> SPECIAL_MARKETS = List.of(
            "First TD Scorer",
            "Last TD Scorer",
            "MVP"
    );

    // All NFL markets combined
    public static final List<String> ALL_MARKETS = Stream.of(
                    PLAYER_MARKETS,
                    TEAM_MARKETS,
                    SPECIAL_MARKETS
            )
            .flatMap(List::stream)
            .distinct()
            .toList();

    private NflMarketTypes() {} // Prevent instantiation
}
