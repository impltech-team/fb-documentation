package io.limeup.flexbets.sport.service;

import java.util.List;
import java.util.Optional;

public interface ExternalIdReadService<T, D, I> extends ReadService<T, D, I> {

    Optional<T> readByExternalId(Integer externalId);

    List<T> readByExternalIdIn(List<Integer> externalIds);

    List<Integer> findAllExternalIds();
}
