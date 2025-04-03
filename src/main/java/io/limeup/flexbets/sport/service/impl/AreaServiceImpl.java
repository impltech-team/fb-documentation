package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.mapper.AreaMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.repository.AreaRepository;
import io.limeup.flexbets.sport.service.AbstractReadService;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.StatScorePaginationUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl extends AbstractReadService<Area, AreaDTO, Long> implements AreaService {

    private final StatScoreProxyService statScoreProxyService;

    private final AreaMapper areaMapper;

    private final AreaRepository areaRepository;

    protected AreaServiceImpl(JpaRepository<Area, Long> repository, StatScoreProxyService statScoreProxyService,
                              AreaMapper areaMapper, AreaRepository areaRepository) {
        super(repository);
        this.statScoreProxyService = statScoreProxyService;
        this.areaMapper = areaMapper;
        this.areaRepository = areaRepository;
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
        repository.saveAll(areas);
    }
}
