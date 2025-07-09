package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.model.dto.ScoreBasicDto;
import org.springframework.stereotype.Component;

@Component
public class IoEventMapper {

    public IoEvent toEntity(ScoreBasicDto dto) {
        return IoEvent.builder()
                .globalGameId(dto.globalGameId())
                .gameId(dto.gameId())
                .season(dto.season())
                .seasonType(dto.seasonType())
                .status(dto.status())
                .day(dto.day())
                .datetime(dto.dateTime())
                .datetimeUtc(dto.dateTimeUtc())
                .awayTeam(dto.awayTeam())
                .homeTeam(dto.homeTeam())
                .awayTeamId(dto.awayTeamId())
                .homeTeamId(dto.homeTeamId())
                .globalAwayTeamId(dto.globalAwayTeamId())
                .globalHomeTeamId(dto.globalHomeTeamId())
                .stadiumId(dto.stadiumId())
                .isClosed(dto.isClosed())
                .updated(dto.updated())
                .awayTeamRuns(dto.awayTeamRuns())
                .homeTeamRuns(dto.homeTeamRuns())
                .awayTeamHits(dto.awayTeamHits())
                .homeTeamHits(dto.homeTeamHits())
                .awayTeamErrors(dto.awayTeamErrors())
                .homeTeamErrors(dto.homeTeamErrors())
                .gameEndDatetime(dto.gameEndDateTime())
                .neutralVenue(dto.neutralVenue())
                .build();
    }

    public void merge(IoEvent entity, ScoreBasicDto dto) {
        entity.setStatus(dto.status());
        entity.setUpdated(dto.updated());
        entity.setAwayTeamRuns(dto.awayTeamRuns());
        entity.setHomeTeamRuns(dto.homeTeamRuns());

    }
}
