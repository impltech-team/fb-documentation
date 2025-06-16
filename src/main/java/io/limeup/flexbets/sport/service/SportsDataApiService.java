//package io.limeup.flexbets.sport.service;
//
//import io.limeup.flexbets.sport.dto.SportsDataPlayerDTO;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.List;
//
//@Service
//public class SportsDataApiService {
//
//    public List<SportsDataPlayerDTO> fetchPlayers() {
//        WebClient webClient = WebClient.builder()
//                .baseUrl("https://api.sportsdata.io/v3/nba/scores/json")
//                .defaultHeader("Ocp-Apim-Subscription-Key", "72a51fdcb7fa43f1870b79cd8a9ab5da")
//                .defaultHeader("Accept", "application/json")
//                .build();
//
//        return webClient.get()
//                .uri("/Players?key=72a51fdcb7fa43f1870b79cd8a9ab5da")
//                .retrieve()
//                .bodyToFlux(SportsDataPlayerDTO.class)
//                .collectList()
//                .block();
//    }
//}
