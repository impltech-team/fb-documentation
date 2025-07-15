package io.limeup.flexbets.sport.service.resolver;

import io.limeup.flexbets.sport.service.ParticipantService;
import io.limeup.flexbets.sport.service.impl.sportsdataio.SportsDataIoParticipantServiceImpl;
import io.limeup.flexbets.sport.service.impl.statscore.ParticipantServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ParticipantServiceResolver {
    private final Map<String, ParticipantService> serviceMap;

    public ParticipantServiceResolver(List<ParticipantService> services) {
        this.serviceMap = Map.of(
                "5466", findService(services, SportsDataIoParticipantServiceImpl.class),
                "5611", findService(services, SportsDataIoParticipantServiceImpl.class),
                "DEFAULT", findService(services, ParticipantServiceImpl.class)
        );
    }

    public ParticipantService resolve(String competitionCode) {
        return serviceMap.getOrDefault(competitionCode, serviceMap.get("DEFAULT"));
    }

    private ParticipantService findService(List<ParticipantService> services, Class<?> clazz) {
        return services.stream()
                .filter(s -> clazz.isAssignableFrom(s.getClass()))
                .findFirst()
                .orElseThrow();
    }
}