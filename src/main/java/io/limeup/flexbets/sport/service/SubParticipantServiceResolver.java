package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.service.impl.sportsdataio.SportsDataIoSubParticipantServiceImpl;
import io.limeup.flexbets.sport.service.impl.statscore.StatscoreSubParticipantServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SubParticipantServiceResolver {
    private final Map<String, SubParticipantService> serviceMap;

    public SubParticipantServiceResolver(List<SubParticipantService> services) {
        this.serviceMap = Map.of(
                "5466", findService(services, SportsDataIoSubParticipantServiceImpl.class),
                "DEFAULT", findService(services, StatscoreSubParticipantServiceImpl.class)
        );
    }

    public SubParticipantService resolve(String competitionCode) {
        return serviceMap.getOrDefault(competitionCode, serviceMap.get("DEFAULT"));
    }

    private SubParticipantService findService(List<SubParticipantService> services, Class<?> clazz) {
        return services.stream()
                .filter(s -> clazz.isAssignableFrom(s.getClass()))
                .findFirst()
                .orElseThrow();
    }
}
