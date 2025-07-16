package io.limeup.flexbets.sport.service.resolver;

import io.limeup.flexbets.sport.service.EventService;

import io.limeup.flexbets.sport.service.impl.sportsdataio.EventServiceIoNflImpl;
import io.limeup.flexbets.sport.service.impl.statscore.StatScoreEventServiceImpl;
import io.limeup.flexbets.sport.service.impl.sportsdataio.EventServiceIoMlbImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EventServiceResolver {
    private final Map<String, EventService> serviceMap;

    public EventServiceResolver(List<EventService> services) {
        this.serviceMap = Map.of(
                "5466", findService(services, EventServiceIoMlbImpl.class),
                "5611", findService(services, EventServiceIoNflImpl.class),
                "DEFAULT", findService(services, StatScoreEventServiceImpl.class)
        );
    }

    public EventService resolve(String competitionCode) {
        return serviceMap.getOrDefault(competitionCode, serviceMap.get("DEFAULT"));
    }

    private EventService findService(List<EventService> services, Class<?> clazz) {
        return services.stream()
                .filter(s -> clazz.isAssignableFrom(s.getClass()))
                .findFirst()
                .orElseThrow();
    }
}
