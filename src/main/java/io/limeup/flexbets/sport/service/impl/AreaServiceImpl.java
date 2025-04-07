package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.mapper.AreaMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.repository.AreaRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.StatScoreDataUtils;
import io.limeup.flexbets.sport.utils.StatScorePaginationUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AreaServiceImpl extends ExternalIdReadServiceImpl<Area, AreaDTO, Long> implements AreaService {

    private final StatScoreProxyService statScoreProxyService;

    private final AreaMapper areaMapper;

    protected AreaServiceImpl(AreaRepository repository, StatScoreProxyService statScoreProxyService,
                              AreaMapper areaMapper) {
        super(repository);
        this.statScoreProxyService = statScoreProxyService;
        this.areaMapper = areaMapper;
    }

    @Override
    public List<AreaDTO> listAreas(List<Integer> areaIds, String name, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public void fetchAreaData() {
        List<StatScoreAreaDTO> fetchedAreas = StatScorePaginationUtils.fetchAllPaginatedData(
                query -> statScoreProxyService.listAreas(query, true),
                Function.identity(),
                AreaQueryParams::new,
                (query, page) -> {
                    query.setPage(page);
                    query.setLimit(500);
                    return query;
                }
        );
        StatScoreDataUtils.mergeAndSaveDTOs(
                fetchedAreas,
                StatScoreAreaDTO::getId,
                ids -> repository.findByExternalIdIn(new ArrayList<>(ids)),
                (dto, existing) -> areaMapper.updateEntity(existing, dto),
                areaMapper::toEntity,
                repository::saveAllAndFlush
        );
    }

    @Override
    @Cacheable(value = "areas", key = "#externalId")
    public Optional<Area> readByExternalId(Integer externalId) {
        return super.readByExternalId(externalId);
    }

    @Override
    @Cacheable(value = "areasBatch", key = "#externalIds")
    public List<Area> readByExternalIdIn(List<Integer> externalIds) {
        return super.readByExternalIdIn(externalIds);
    }
}
