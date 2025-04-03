package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.VenueDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.limeup.flexbets.sport.mapper.VenueMapper;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.VenueRepository;
import io.limeup.flexbets.sport.service.AbstractReadService;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.StatScorePaginationUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenueServiceImpl extends AbstractReadService<Venue, VenueDTO, Long> implements VenueService {

    private final StatScoreProxyService statScoreProxyService;

    private final VenueMapper venueMapper;

    protected VenueServiceImpl(VenueRepository repository, StatScoreProxyService statScoreProxyService, VenueMapper venueMapper) {
        super(repository);
        this.statScoreProxyService = statScoreProxyService;
        this.venueMapper = venueMapper;
    }

    @Override
    public void fetchVenueData() {
        List<Venue> venues = StatScorePaginationUtils.fetchAllPaginatedData(
                statScoreProxyService::listVenues,
                venueMapper::toEntity,
                VenueQueryParams::new,
                (query, page) -> {
                    query.setPage(page);
                    query.setLimit(500);
                    return query;
                }
        );
        repository.saveAll(venues);
    }
}
