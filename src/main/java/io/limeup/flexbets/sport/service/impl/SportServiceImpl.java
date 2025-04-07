package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.mapper.SportMapper;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.repository.SportRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.SportService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.StatScorePaginationUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportServiceImpl extends ExternalIdReadServiceImpl<Sport, SportDTO, Long> implements SportService {

    private final StatScoreProxyService statScoreProxyService;

    private final SportMapper sportMapper;

    protected SportServiceImpl(SportRepository repository, StatScoreProxyService statScoreProxyService,
                               SportMapper sportMapper) {
        super(repository);
        this.statScoreProxyService = statScoreProxyService;
        this.sportMapper = sportMapper;
    }

    @Override
    public List<SportDTO> listSports(List<Integer> sportIds, String name,
                                     RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public SportDTO getSportById(Integer sportId) {
        return null;
    }

    @Override
    public void fetchSportData() {
        List<Sport> sports = StatScorePaginationUtils.fetchAllPaginatedData(
                statScoreProxyService::listSports,
                sportMapper::toEntity,
                SportQueryParams::new,
                (query, page) -> {
                    query.setPage(page);
                    query.setLimit(500);
                    return query;
                }
        );
        repository.saveAllAndFlush(sports);
    }
}
