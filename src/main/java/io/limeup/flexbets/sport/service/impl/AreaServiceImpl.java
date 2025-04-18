package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
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
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Transactional
@Service
public class AreaServiceImpl extends ExternalIdReadServiceImpl<Area, AreaDTO, Long> implements AreaService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("name", "id");

    private final AreaRepository areaRepository;

    private final StatScoreProxyService statScoreProxyService;

    private final AreaMapper areaMapper;

    protected AreaServiceImpl(AreaRepository repository, StatScoreProxyService statScoreProxyService,
                              AreaMapper areaMapper) {
        super(repository);
        this.statScoreProxyService = statScoreProxyService;
        this.areaMapper = areaMapper;
        this.areaRepository = repository;
    }

    @Override
    public PaginatedResponse<AreaDTO> listAreas(List<Integer> areaIds, String name, RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
        PageRequest pageRequest = PaginationUtils.getPageRequest(requestQuery);
        List<Integer> safeList = areaIds == null || areaIds.isEmpty() ? List.of(-1) : areaIds;
        boolean areaIdsEmpty = areaIds == null || areaIds.isEmpty();

        Page<Area> pagedResult = areaRepository.listAreas(safeList, areaIdsEmpty, name, pageRequest);

        return PaginationUtils.buildPaginatedResponse(AreaMapper.toDTO(pagedResult.getContent()), pagedResult.getTotalElements(),
                requestQuery.getPage(), requestQuery.getPageSize());
    }

    @Override
    public void fetchAreaData() {
        List<StatScoreAreaDTO> fetchedAreas = PaginationUtils.fetchAllPaginatedData(
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
                ids -> areaRepository.findByExternalIdIn(new ArrayList<>(ids)),
                (dto, existing) -> areaMapper.updateEntity(existing, dto),
                areaMapper::toEntity,
                Area::getExternalId,
                areaRepository::saveAllAndFlush
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
