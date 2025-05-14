package io.limeup.flexbets.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ExternalIdRepository<T, I> extends JpaRepository<T, I> {

    Optional<T> findByExternalId(Integer externalId);

    List<T> findByExternalIdIn(Collection<Integer> externalIds);

    void deleteByExternalId(Integer externalId);

    @Query("SELECT c.externalId FROM Competition c")
    List<Integer> findAllExternalIds();
}
