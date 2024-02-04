package com.qrestor.commons.mapper;

import com.qrestor.models.dto.BasicDTO;
import org.mapstruct.BeanMapping;

import java.util.List;

public interface BasicMapper<D extends BasicDTO, E> {
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    D toDto(E entity);

    List<D> toDto(Iterable<E> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    E toEntity(D dto);

    List<E> toEntity(Iterable<D> dto);
}
