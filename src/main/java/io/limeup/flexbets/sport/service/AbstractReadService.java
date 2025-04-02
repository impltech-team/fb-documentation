package io.limeup.flexbets.sport.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.Function;

public class AbstractReadService<T, D, ID> implements ReadService<T, D, ID> {

    protected final JpaRepository<T, ID> repository;

    protected AbstractReadService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T readById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found: " + id));
    }

    @Override
    public List<T> readByIds(List<ID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public D readDTOById(ID id, Function<T, D> toDto) {
        return toDto.apply(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found: " + id)));
    }

    @Override
    public List<D> readDTOByIds(List<ID> ids, Function<T, D> toDto) {
        return repository.findAllById(ids).stream()
                .map(toDto)
                .toList();
    }
}
