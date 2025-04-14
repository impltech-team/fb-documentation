package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.dto.SportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.model.Sport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SportMapper {

    public Sport toEntity(StatScoreSportLiteDTO dto) {
        if (dto == null) return null;

        Sport entity = new Sport();
        return updateEntity(entity, dto);
    }

    public Sport toEntity(StatScoreSportDTO dto) {
        if (dto == null) return null;

        Sport entity = new Sport();
        return updateEntity(entity, dto);
    }

    public Sport updateEntity(Sport entity, StatScoreSportLiteDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public Sport updateEntity(Sport entity, StatScoreSportDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static List<SportLiteDTO> toLiteDTO(List<Sport> sports) {
        if (sports == null) return Collections.emptyList();

        return sports.stream()
                .map(s -> {
                    SportLiteDTO dto = new SportLiteDTO();
                    dto.setId(s.getExternalId());
                    dto.setName(s.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public static SportDTO statScoreToFlexBetsDTO(StatScoreSportDTO statScoreSportDTO) {
        if (statScoreSportDTO == null) return null;

        SportDTO dto = new SportDTO();
        dto.setId(statScoreSportDTO.getId());
        dto.setName(statScoreSportDTO.getName());

        List<SportDTO.Status> statuses = Optional.ofNullable(statScoreSportDTO.getStatuses())
                .orElse(Collections.emptyList())
                .stream()
                .map(s -> {
                    SportDTO.Status statusDTO = new SportDTO.Status();
                    statusDTO.setStatusName(s.getName());
                    statusDTO.setDescription(s.getShortName());
                    return statusDTO;
                })
                .collect(Collectors.toList());
        dto.setStatuses(statuses);

        List<SportDTO.ResultType> resultTypes = Optional.ofNullable(statScoreSportDTO.getResults())
                .orElse(Collections.emptyList())
                .stream()
                .map(r -> {
                    SportDTO.ResultType resultDTO = new SportDTO.ResultType();
                    resultDTO.setType(r.getType());
                    resultDTO.setDescription(r.getName());
                    return resultDTO;
                })
                .collect(Collectors.toList());
        dto.setResultTypes(resultTypes);

        List<SportDTO.EventDetails> eventDetails = Optional.ofNullable(statScoreSportDTO.getDetails())
                .orElse(Collections.emptyList())
                .stream()
                .map(detail -> {
                    SportDTO.EventDetails stat = new SportDTO.EventDetails();
                    stat.setName(detail.getCode());
                    stat.setDescription(detail.getName());
                    return stat;
                })
                .collect(Collectors.toList());
        dto.setEventDetails(eventDetails);

        List<SportDTO.TeamStatistic> teamStats = Optional.ofNullable(statScoreSportDTO.getStats())
                .map(StatScoreSportDTO.SportStats::getTeam)
                .orElse(Collections.emptyList())
                .stream()
                .map(stat -> {
                    SportDTO.TeamStatistic dtoStat = new SportDTO.TeamStatistic();
                    dtoStat.setStatName(stat.getCode());
                    dtoStat.setDescription(stat.getName());
                    return dtoStat;
                })
                .collect(Collectors.toList());
        dto.setTeamStatistics(teamStats);

        List<SportDTO.PlayerStatistic> playerStats = Optional.ofNullable(statScoreSportDTO.getStats())
                .map(StatScoreSportDTO.SportStats::getPerson)
                .orElse(Collections.emptyList())
                .stream()
                .map(stat -> {
                    SportDTO.PlayerStatistic dtoStat = new SportDTO.PlayerStatistic();
                    dtoStat.setStatName(stat.getCode());
                    dtoStat.setDescription(stat.getName());
                    return dtoStat;
                })
                .collect(Collectors.toList());
        dto.setPlayerStatistics(playerStats);

        List<SportDTO.Incident> incidents = Optional.ofNullable(statScoreSportDTO.getIncidents())
                .orElse(Collections.emptyList())
                .stream()
                .map(incident -> {
                    SportDTO.Incident dtoIncident = new SportDTO.Incident();
                    dtoIncident.setIncidentType(incident.getCode());
                    dtoIncident.setDescription(incident.getName());
                    return dtoIncident;
                })
                .collect(Collectors.toList());
        dto.setIncidents(incidents);

        return dto;
    }


}
