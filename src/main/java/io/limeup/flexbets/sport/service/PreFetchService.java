package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.PrefetchStatusDTO;

import java.util.List;

public interface PreFetchService {
    List<PrefetchStatusDTO> preFetchStatus();
}
