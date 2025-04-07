package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.VenueDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.limeup.flexbets.sport.mapper.VenueMapper;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.VenueRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.StatScorePaginationUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl extends ExternalIdReadServiceImpl<Venue, VenueDTO, Long> implements VenueService {

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
        repository.saveAllAndFlush(venues);
    }
}
