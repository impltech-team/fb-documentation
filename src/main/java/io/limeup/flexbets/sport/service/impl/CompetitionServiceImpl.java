package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.CompetitionQueryParams;
import io.limeup.flexbets.sport.mapper.CompetitionMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.repository.CompetitionRepository;
import io.limeup.flexbets.sport.service.AbstractReadService;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.service.SportService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CompetitionServiceImpl extends AbstractReadService<Competition, CompetitionDTO, Long> implements CompetitionService {

    private final StatScoreProxyService statScoreProxyService;

    private final CompetitionMapper competitionMapper;

    private final CompetitionRepository competitionRepository;

    private final SportService sportService;

    private final AreaService areaService;

    protected CompetitionServiceImpl(JpaRepository<Competition, Long> repository, StatScoreProxyService statScoreProxyService,
                                     CompetitionMapper competitionMapper, CompetitionRepository competitionRepository,
                                     SportService sportService, AreaService areaService) {
        super(repository);
        this.statScoreProxyService = statScoreProxyService;
        this.competitionMapper = competitionMapper;
        this.competitionRepository = competitionRepository;
        this.sportService = sportService;
        this.areaService = areaService;
    }

    @Override
    public List<CompetitionDTO> listCompetitions(List<Integer> areaIds, List<Integer> sportIds, String dateFrom,
                                                 String dateTo, String type, String gender, String statusType,
                                                 RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public Competition create(StatScoreCompetitionDTO dto) {
        Sport sport = sportService.readById((long) dto.getSportId()).get();
        Area area = areaService.readById((long) dto.getAreaId()).get();
        return repository.save(competitionMapper.toEntity(dto, sport, area));
    }

    @Override
    public void fetchCompetitionData() {
        List<StatScoreCompetitionDTO> competitionDTOs = statScoreProxyService
                .listCompetitions(new CompetitionQueryParams())
                .getItems();

        List<Long> sportIds = competitionDTOs.stream()
                .map(dto -> (long) dto.getSportId())
                .distinct()
                .toList();

        List<Long> areaIds = competitionDTOs.stream()
                .map(dto -> (long) dto.getAreaId())
                .distinct()
                .toList();

        Map<Long, Sport> sportsMap = sportService.readByIds(sportIds).stream()
                .collect(Collectors.toMap(Sport::getId, Function.identity()));

        Map<Long, Area> areasMap = areaService.readByIds(areaIds).stream()
                .collect(Collectors.toMap(Area::getId, Function.identity()));

        List<Competition> competitions = competitionDTOs.stream()
                .map(dto -> {
                    Sport sport = sportsMap.get((long) dto.getSportId());
                    Area area = areasMap.get((long) dto.getAreaId());
                    return competitionMapper.toEntity(dto, sport, area);
                })
                .toList();

        competitionRepository.saveAll(competitions);
    }
}
