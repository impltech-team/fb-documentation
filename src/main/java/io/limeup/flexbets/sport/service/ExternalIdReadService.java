package io.limeup.flexbets.sport.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ExternalIdReadService<T, D, ID> extends ReadService<T, D, ID> {

    Optional<T> readByExternalId(Integer externalId);

    List<T> readByExternalIdIn(List<Integer> externalIds);
}
