package io.limeup.flexbets.sport.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ReadServiceImpl<T, D, I> implements ReadService<T, D, I>  {

    protected final JpaRepository<T, I> repository;

    public ReadServiceImpl(JpaRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> readById(I id) {
        return repository.findById(id);
    }

    @Override
    public List<T> readByIds(List<I> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<D> readDTOByIds(List<I> ids, Function<T, D> toDto) {
        return repository.findAllById(ids).stream()
                .map(toDto)
                .toList();
    }

    @Override
    public T readByIdSafe(I id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found: " + id));
    }

    @Override
    public D readDTOByIdSafe(I id, Function<T, D> toDto) {
        return toDto.apply(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found: " + id)));
    }

}
