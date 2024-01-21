package com.qrestor.commons;

import com.qrestor.commons.CrudService;
import com.qrestor.commons.dto.AbstractDTO;
import com.qrestor.commons.mapper.CrudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public abstract class AbstractCrudService<D extends AbstractDTO, E> implements CrudService<D> {

    @Autowired
    private CrudMapper<D, E> mapper;
    @Autowired
    private JpaRepository<E, Long> repository;

    @Override
    public D create(D dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public D update(Long id, D dto) {
        return repository.findById(id).map(entity -> {
            E updatedEntity = mapper.toEntity(dto);
            return mapper.toDto(repository.save(updatedEntity));
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public D findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    @Override
    public List<D> findAll(Pageable pageable) {
        return mapper.toDto(repository.findAll(pageable));
    }
}
