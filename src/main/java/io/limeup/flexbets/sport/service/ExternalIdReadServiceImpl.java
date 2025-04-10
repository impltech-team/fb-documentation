package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.repository.ExternalIdRepository;

import java.util.List;
import java.util.Optional;

public class ExternalIdReadServiceImpl<T, D, ID> extends ReadServiceImpl<T, D, ID> implements ExternalIdReadService<T, D, ID> {

    protected final ExternalIdRepository<T, ID> repository;

    protected ExternalIdReadServiceImpl(ExternalIdRepository<T, ID> repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<T> readByExternalId(Integer externalId) {
        return repository.findByExternalId(externalId);
    }

    @Override
    public List<T> readByExternalIdIn(List<Integer> externalIds) {
        return repository.findByExternalIdIn(externalIds);
    }
}
