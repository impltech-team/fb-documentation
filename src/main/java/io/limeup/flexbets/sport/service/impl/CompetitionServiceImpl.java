package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.CompetitionQueryParams;
import io.limeup.flexbets.sport.mapper.CompetitionMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.CompetitionType;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.model.StatusType;
import io.limeup.flexbets.sport.repository.CompetitionRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.service.SportService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CompetitionServiceImpl extends ExternalIdReadServiceImpl<Competition, CompetitionDTO, Long> implements CompetitionService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("name", "id");

    private final StatScoreProxyService statScoreProxyService;

    private final CompetitionRepository competitionRepository;

    private final CompetitionMapper competitionMapper;

    private final SportService sportService;

    private final AreaService areaService;

    protected CompetitionServiceImpl(CompetitionRepository repository, StatScoreProxyService statScoreProxyService,
                                     CompetitionMapper competitionMapper, SportService sportService,
                                     AreaService areaService) {
        super(repository);
        this.statScoreProxyService = statScoreProxyService;
        this.competitionRepository = repository;
        this.competitionMapper = competitionMapper;
        this.sportService = sportService;
        this.areaService = areaService;
    }

    @Override
    public PaginatedResponse<CompetitionDTO> listCompetitions(Integer areaId, Integer sportId, CompetitionType type, String gender, StatusType statusType,
                                                              RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
        PageRequest pageRequest = PaginationUtils.getPageRequest(requestQuery);
        Page<Competition> pagedResult = competitionRepository.listCompetitions(areaId, sportId, type, gender, statusType, pageRequest);

        return PaginationUtils.buildPaginatedResponse(CompetitionMapper.toDTO(pagedResult.getContent()), pagedResult.getTotalElements(),
                requestQuery.getPage(), requestQuery.getPageSize());
    }

    @Override
    public Competition create(StatScoreCompetitionDTO dto) {
        Sport sport = sportService.readByExternalId(dto.getSportId()).get();
        Area area = areaService.readByExternalId(dto.getAreaId()).get();
        return competitionRepository.save(competitionMapper.toEntity(dto, sport, area));
    }

    @Override
    public void fetchCompetitionData() {
        List<StatScoreCompetitionDTO> competitionDTOs = statScoreProxyService
                .listCompetitions(new CompetitionQueryParams(), true)
                .getItems();

        List<Integer> sportIds = competitionDTOs.stream()
                .map(StatScoreCompetitionDTO::getSportId)
                .distinct()
                .toList();

        List<Integer> areaIds = competitionDTOs.stream()
                .map(StatScoreCompetitionDTO::getAreaId)
                .distinct()
                .toList();

        Map<Long, Sport> sportsMap = sportService.readByExternalIdIn(sportIds).stream()
                .collect(Collectors.toMap(Sport::getId, Function.identity()));

        Map<Long, Area> areasMap = areaService.readByExternalIdIn(areaIds).stream()
                .collect(Collectors.toMap(Area::getId, Function.identity()));

        List<Competition> competitions = competitionDTOs.stream()
                .map(dto -> {
                    Sport sport = sportsMap.get((long) dto.getSportId());
                    Area area = areasMap.get((long) dto.getAreaId());
                    return competitionMapper.toEntity(dto, sport, area);
                })
                .toList();

        competitionRepository.saveAllAndFlush(competitions);
    }
}
