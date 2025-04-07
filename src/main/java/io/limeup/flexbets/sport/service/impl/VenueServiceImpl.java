package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.VenueDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.limeup.flexbets.sport.mapper.VenueMapper;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.VenueRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.StatScoreDataUtils;
import io.limeup.flexbets.sport.utils.StatScorePaginationUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
    public Venue create(StatScoreVenueDTO venueDTO) {
        return repository.save(venueMapper.toEntity(venueDTO));
    }

    @Override
    public void fetchVenueData() {
        List<StatScoreVenueDTO> fetchedVenues = StatScorePaginationUtils.fetchAllPaginatedData(
                query -> statScoreProxyService.listVenues(query, true),
                Function.identity(),
                VenueQueryParams::new,
                (query, page) -> {
                    query.setPage(page);
                    query.setLimit(500);
                    return query;
                }
        );
        StatScoreDataUtils.mergeAndSaveDTOs(
                fetchedVenues,
                StatScoreVenueDTO::getId,
                ids -> repository.findByExternalIdIn(new ArrayList<>(ids)),
                (dto, existing) -> venueMapper.updateEntity(existing, dto),
                venueMapper::toEntity,
                repository::saveAllAndFlush
        );
    }
}
