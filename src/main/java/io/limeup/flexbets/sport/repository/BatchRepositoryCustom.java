package io.limeup.flexbets.sport.repository;

import java.util.Collection;
import java.util.List;

public interface BatchRepositoryCustom<T> {

    List<T> saveOrUpdateBatch(Collection<T> participants);

}
