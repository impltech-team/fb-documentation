package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.dto.SportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.mapper.SportMapper;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.repository.SportRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.SportService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.StatScoreDataUtils;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class SportServiceImpl extends ExternalIdReadServiceImpl<Sport, SportDTO, Long> implements SportService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("name", "id");

    private final SportRepository sportRepository;

    private final StatScoreProxyService statScoreProxyService;

    private final SportMapper sportMapper;

    protected SportServiceImpl(SportRepository repository, StatScoreProxyService statScoreProxyService,
                               SportMapper sportMapper) {
        super(repository);
        this.sportRepository = repository;
        this.statScoreProxyService = statScoreProxyService;
        this.sportMapper = sportMapper;
    }

    @Override
    public PaginatedResponse<SportLiteDTO> listSports(List<Integer> sportIds, String name,
                                                      RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
        PageRequest pageRequest = PaginationUtils.getPageRequest(requestQuery);
        List<Integer> safeList = sportIds == null || sportIds.isEmpty() ? List.of(-1) : sportIds;
        boolean sportIdsEmpty = sportIds == null || sportIds.isEmpty();
        Page<Sport> pagedResult = sportRepository.listSports(safeList, sportIdsEmpty, name, pageRequest);

        return PaginationUtils.buildPaginatedResponse(SportMapper.toLiteDTO(pagedResult.getContent()), pagedResult.getTotalElements(),
                requestQuery.getPage(), requestQuery.getPageSize());
    }

    @Override
    public SportDTO getSportById(Integer sportId) {
        return SportMapper.statScoreToFlexBetsDTO(statScoreProxyService.getSportById(sportId, false));
    }

    @Override
    public void fetchSportData() {
        List<StatScoreSportLiteDTO> fetchedSports = PaginationUtils.fetchAllPaginatedData(
                query -> statScoreProxyService.listSports(query, true),
                Function.identity(),
                SportQueryParams::new,
                (query, page) -> {
                    query.setPage(page);
                    query.setLimit(500);
                    return query;
                }
        );
        StatScoreDataUtils.mergeAndSaveDTOs(
                fetchedSports,
                StatScoreSportLiteDTO::getId,
                ids -> sportRepository.findByExternalIdIn(new ArrayList<>(ids)),
                (dto, existing) -> sportMapper.updateEntity(existing, dto),
                sportMapper::toEntity,
                Sport::getExternalId,
                sportRepository::saveAllAndFlush
        );
    }
}
