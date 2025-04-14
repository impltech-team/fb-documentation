package io.limeup.flexbets.sport.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ReadService<T, D, I> {

    Optional<T> readById(I id);

    T readByIdSafe(I id);

    List<T> readByIds(List<I> ids);

    D readDTOByIdSafe(I id, Function<T, D> toDto);

    List<D> readDTOByIds(List<I> ids, Function<T, D> toDto);

}
