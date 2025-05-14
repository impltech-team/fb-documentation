package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.model.Area;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AreaMapper {

    public Area toEntity(StatScoreAreaDTO dto) {
        if (dto == null) return null;

        Area entity = new Area();
        return updateEntity(entity, dto);
    }

    public Area updateEntity(Area entity, StatScoreAreaDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());
        entity.setAreaCode(dto.getAreaCode());
        entity.setParentAreaId(dto.getParentAreaId());

        return entity;
    }

    public static List<AreaDTO> toDTO(List<Area> areaList) {
        if (areaList == null) return Collections.emptyList();

        return areaList.stream().map(area -> {
            AreaDTO dto = new AreaDTO();
            dto.setId(area.getExternalId());
            dto.setName(area.getName());
            dto.setCountryCode(area.getAreaCode());
            return dto;
        }).collect(Collectors.toList());
    }
}
