package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.mapper.AreaMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.repository.AreaRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.StatScorePaginationUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<Area> areas = StatScorePaginationUtils.fetchAllPaginatedData(
                statScoreProxyService::listAreas,
                areaMapper::toEntity,
                AreaQueryParams::new,
                (query, page) -> {
                    query.setPage(page);
                    query.setLimit(500);
                    return query;
                }
        );
        repository.saveAllAndFlush(areas);
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
