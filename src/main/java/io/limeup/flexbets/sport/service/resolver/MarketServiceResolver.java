package io.limeup.flexbets.sport.service.resolver;

import io.limeup.flexbets.sport.service.MarketService;
import io.limeup.flexbets.sport.service.impl.sportsdataio.MarketServiceIoMlbImpl;
import io.limeup.flexbets.sport.service.impl.statscore.MarketServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MarketServiceResolver {
    private final Map<String, MarketService> serviceMap;

    public MarketServiceResolver(List<MarketService> services) {
        this.serviceMap = Map.of(
                "5466", findService(services, MarketServiceIoMlbImpl.class),
                "DEFAULT", findService(services, MarketServiceImpl.class)
        );
    }

    public MarketService resolve(String competitionCode) {
        return serviceMap.getOrDefault(competitionCode, serviceMap.get("DEFAULT"));
    }

    private MarketService findService(List<MarketService> services, Class<?> clazz) {
        return services.stream()
                .filter(s -> clazz.isAssignableFrom(s.getClass()))
                .findFirst()
                .orElseThrow();
    }
}