package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatsScoreParticipantDTO;
import org.springframework.stereotype.Component;

@Component
public class StatScoreMapper {

    public ParticipantDTO mapToParticipantDTO(StatsScoreParticipantDTO source) {
        if (source == null) return null;

        ParticipantDTO.Details details = null;
        if (source.getDetails() != null) {
            var d = source.getDetails();
            details = new ParticipantDTO.Details();
            details.setFounded(d.getFounded());
            details.setPhone(d.getPhone());
            details.setEmail(d.getEmail());
            details.setAddress(d.getAddress());
            details.setVenueId(d.getVenueId());
            details.setVenueName(d.getVenueName());
            details.setWeight(d.getWeight());
            details.setHeight(d.getHeight());
            details.setNickname(d.getNickname());
            details.setPositionName(d.getPositionName());
            details.setPositionId(d.getPositionId());
            details.setBirthdate(d.getBirthdate());
            details.setBornPlace(d.getBornPlace());
            details.setIsRetired(d.getIsRetired());
            details.setSubtype(d.getSubtype());
        }

        return new ParticipantDTO(
                source.getId(),
                source.getName(),
                source.getAcronym(),
                "NBA",
                1,
                null,
                null,
                null,
                details
        );
    }
}
