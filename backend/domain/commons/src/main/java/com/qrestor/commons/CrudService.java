package com.qrestor.commons;

import com.qrestor.commons.dto.AbstractDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService<D extends AbstractDTO> {

    D create(D dto);

    D update(Long id, D dto);

    void delete(Long id);

    D findById(Long id);

    List<D> findAll(Pageable pageable);
}
