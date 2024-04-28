package com.qrestor.commons;

import com.qrestor.models.dto.AbstractPublicDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CrudService<D extends AbstractPublicDTO> {

    D create(D dto);

    D update(D dto);

    void delete(UUID id);

    D findById(UUID id);

    D findByIdPublic(UUID id);

    List<D> findAll(Pageable pageable);

    List<D> findTotallyAll();
}
