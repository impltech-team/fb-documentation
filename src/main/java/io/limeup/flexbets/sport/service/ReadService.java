package io.limeup.flexbets.sport.service;

import java.util.List;
import java.util.function.Function;

public interface ReadService<T, D, ID> {

    T readById(ID id);

    List<T> readByIds(List<ID> ids);

    D readDTOById(ID id, Function<T, D> toDto);

    List<D> readDTOByIds(List<ID> ids, Function<T, D> toDto);
}
