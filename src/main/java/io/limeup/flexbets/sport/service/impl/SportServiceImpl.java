package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.mapper.SportMapper;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.repository.SportRepository;
import io.limeup.flexbets.sport.service.AbstractReadService;
import io.limeup.flexbets.sport.service.SportService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SportServiceImpl extends AbstractReadService<Sport, SportDTO, Long> implements SportService {

    private final StatScoreProxyService statScoreProxyService;

    private final SportMapper sportMapper;

    private final SportRepository sportRepository;

    protected SportServiceImpl(SportRepository repository, StatScoreProxyService statScoreProxyService, SportMapper sportMapper, SportRepository sportRepository) {
        super(repository);
        this.statScoreProxyService = statScoreProxyService;
        this.sportMapper = sportMapper;
        this.sportRepository = sportRepository;
    }

    @Override
    public List<SportDTO> listSports(List<Integer> sportIds, String name, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public SportDTO getSportById(Integer sportId) {
        return null;
    }

    @Override
    public void fetchSportData() {
        List<Sport> sports = statScoreProxyService.listSports(new SportQueryParams()).getItems()
                .stream()
                .map(sportMapper::toEntity)
                .collect(Collectors.toList());
        sportRepository.saveAll(sports);
    }
}
