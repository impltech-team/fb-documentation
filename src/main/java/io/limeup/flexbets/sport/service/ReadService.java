package io.limeup.flexbets.sport.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ReadService<T, D, ID> {

    Optional<T> readById(ID id);

    T readByIdSafe(ID id);

    List<T> readByIds(List<ID> ids);

    D readDTOByIdSafe(ID id, Function<T, D> toDto);

    List<D> readDTOByIds(List<ID> ids, Function<T, D> toDto);
}
