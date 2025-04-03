package io.limeup.flexbets.sport.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;

@NoRepositoryBean
public interface BatchRepositoryCustom<T> {

    List<T> saveOrUpdateBatch(Collection<T> participants);

}
