package io.limeup.flexbets.sport.repository.impl;

import io.limeup.flexbets.sport.repository.BatchRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class BatchRepositoryImpl<T> implements BatchRepositoryCustom<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<T> saveOrUpdateBatch(Collection<T> entities) {
        List<T> managedEntities = new ArrayList<>(entities.size());
        int i = 0;
        for (T entity : entities) {
            T managed = entityManager.merge(entity);
            managedEntities.add(managed);
            i++;
            if (i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
        return managedEntities;
    }

}
