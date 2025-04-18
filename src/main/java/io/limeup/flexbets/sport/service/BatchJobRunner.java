package io.limeup.flexbets.sport.service;

import org.springframework.http.ResponseEntity;

public interface BatchJobRunner {
    ResponseEntity<String> runStatsPreFetchingJob(Integer days);
}
