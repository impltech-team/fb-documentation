package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.model.IoEventNFL;
import io.limeup.flexbets.sport.model.dto.ScoreNFLDto;
import org.springframework.stereotype.Component;

@Component
public class IoEventNFLMapper {

    public IoEventNFL toEntity(ScoreNFLDto dto) {
        if (dto == null) {
            return null;
        }

        return IoEventNFL.builder()
                .globalGameId(dto.globalGameId())
                .gameKey(dto.gameKey())
                .season(dto.season())
                .seasonType(dto.seasonType())
                .week(dto.week())
                .status(dto.status())
                .day(dto.day())
                .datetime(dto.dateTime())
                .datetimeUtc(dto.dateTimeUtc())
                .awayTeam(dto.awayTeam())
                .homeTeam(dto.homeTeam())
                .awayScore(dto.awayScore())
                .homeScore(dto.homeScore())
                .awayTeamId(dto.awayTeamId())
                .homeTeamId(dto.homeTeamId())
                .globalAwayTeamId(dto.globalAwayTeamId())
                .globalHomeTeamId(dto.globalHomeTeamId())
                .stadiumId(dto.stadiumId())
                .isClosed(dto.isClosed())
                .lastUpdated(dto.lastUpdated())
                .gameEndDatetime(dto.gameEndDateTime())
                .neutralVenue(dto.neutralVenue())
                .quarterDescription(dto.quarterDescription())
                .attendance(dto.attendance())
                .channel(dto.channel())
                .pointSpread(dto.pointSpread())
                .overUnder(dto.overUnder())
                .refereeId(dto.refereeId())
                .build();
    }

    public void merge(IoEventNFL entity, ScoreNFLDto dto) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setGlobalGameId(dto.globalGameId());
        entity.setGameKey(dto.gameKey());
        entity.setSeason(dto.season());
        entity.setSeasonType(dto.seasonType());
        entity.setWeek(dto.week());
        entity.setStatus(dto.status());
        entity.setDay(dto.day());
        entity.setDatetime(dto.dateTime());
        entity.setDatetimeUtc(dto.dateTimeUtc());
        entity.setAwayTeam(dto.awayTeam());
        entity.setHomeTeam(dto.homeTeam());
        entity.setAwayScore(dto.awayScore());
        entity.setHomeScore(dto.homeScore());
        entity.setAwayTeamId(dto.awayTeamId());
        entity.setHomeTeamId(dto.homeTeamId());
        entity.setGlobalAwayTeamId(dto.globalAwayTeamId());
        entity.setGlobalHomeTeamId(dto.globalHomeTeamId());
        entity.setStadiumId(dto.stadiumId());
        entity.setIsClosed(dto.isClosed());
        entity.setLastUpdated(dto.lastUpdated());
        entity.setGameEndDatetime(dto.gameEndDateTime());
        entity.setNeutralVenue(dto.neutralVenue());
        entity.setQuarterDescription(dto.quarterDescription());
        entity.setAttendance(dto.attendance());
        entity.setChannel(dto.channel());
        entity.setPointSpread(dto.pointSpread());
        entity.setOverUnder(dto.overUnder());
        entity.setRefereeId(dto.refereeId());
    }
}
