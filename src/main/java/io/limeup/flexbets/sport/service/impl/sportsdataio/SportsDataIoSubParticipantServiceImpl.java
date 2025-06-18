package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.dto.IoPlayerMapper;
import io.limeup.flexbets.sport.model.enums.IoBetMarketStatus;
import io.limeup.flexbets.sport.repository.projection.BetRow;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import io.limeup.flexbets.sport.repository.sportsdataio.IoBetRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoPlayerRepository;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service("sportsDataIoProvider")
public class SportsDataIoSubParticipantServiceImpl implements SubParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("player_name", "team_name", "position", "event_time");

    private final IoPlayerRepository playerRepository;
    private final IoBetRepository betRepository;
    private final IoPlayerMapper playerMapper;

    public SportsDataIoSubParticipantServiceImpl(IoPlayerRepository playerRepository, IoBetRepository betRepository, IoPlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.betRepository = betRepository;
        this.playerMapper = playerMapper;
    }

    @EventBasedCache(cacheName = "subParticipantsListCache",
            key = "T(java.util.Objects).hash(#competitionId, #positions, #participantIds, #marketId, #maxHistoricalDataCount, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")
    @Override
    public PaginatedResponse<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions,
                                                                    List<Integer> participantIds, Integer marketId,
                                                                    Integer maxHistoricalDataCount, RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);

//        Set<String> statNames = marketService.getStatsByMarket(competitionId, marketId, MarketType.SUB_PARTICIPANT);

        Long count = playerRepository.countPlayersWithUpcomingEvent();

        if (count == 0) {
            return PaginationUtils.buildPaginatedResponse(null, count, requestQuery.getPage(), requestQuery.getPageSize());
        }

        List<SportsDataPlayerRow> players = playerRepository.listPlayersWithUpcomingEvent(
                (requestQuery.getPage() - 1) * requestQuery.getPageSize(),
                requestQuery.getPageSize()
        );

        List<SubParticipantDTO> subParticipantDTOList = playerMapper.toSubParticipantDTOList(players, new HashMap<>());

        return PaginationUtils.buildPaginatedResponse(subParticipantDTOList, count, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @EventBasedCache(cacheName = "subParticipantDetailsCache",
            key = "T(java.util.Objects).hash(#subParticipantId, #marketId, #maxHistoricalDataCount)")
    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId, Integer marketId, Integer maxHistoricalDataCount) {
//        SubParticipant rawSubParticipant = externalIdRepository.findByExternalId(subParticipantId)
//                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("SubParticipant %s Not Found", subParticipantId)));
//        Set<String> statNames = marketService.getStatsByMarket(
//                rawSubParticipant.getCompetition().getExternalId(), marketId, MarketType.SUB_PARTICIPANT);
//        List<SubParticipantStatRow> subParticipantStatsDetails = statRepository.getSubParticipantStatsDetails(
//                subParticipantId, maxHistoricalDataCount, statNames);
//
//        return mapper.toDTO(subParticipantStatsDetails, retrieveEventIdSubParticipantBetMap(subParticipantStatsDetails))
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("SubParticipant %s Not Found", subParticipantId)));
        return null;
    }

    private Map<Long, List<IoBet>> retrieveEventIdSubParticipantBetMap(List<SportsDataPlayerRow> playerDetails) {
        Set<Integer> eventIds = playerDetails.stream()
                .map(SportsDataPlayerRow::getEventId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return betRepository.findAllByMarketTypeAndEventIdInAndAnyBetsAvailableTrue(IoBetMarketStatus.PLAYER_PROP.getName(), eventIds)
                .stream()
                .collect(Collectors.groupingBy(IoBet::getPlayerId));
    }
}
