package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.repository.ExternalIdRepository;

import java.util.List;
import java.util.Optional;

public class ExternalIdReadServiceImpl<T, D, I> extends ReadServiceImpl<T, D, I> implements ExternalIdReadService<T, D, I> {

    protected final ExternalIdRepository<T, I> externalIdRepository;

    protected ExternalIdReadServiceImpl(ExternalIdRepository<T, I> repository) {
        super(repository);
        this.externalIdRepository = repository;
    }

    @Override
    public Optional<T> readByExternalId(Integer externalId) {
        return externalIdRepository.findByExternalId(externalId);
    }

    @Override
    public List<T> readByExternalIdIn(List<Integer> externalIds) {
        return externalIdRepository.findByExternalIdIn(externalIds);
    }
}
