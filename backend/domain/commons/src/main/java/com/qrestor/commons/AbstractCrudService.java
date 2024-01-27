package com.qrestor.commons;

import com.qrestor.commons.dto.AbstractPublicDTO;
import com.qrestor.commons.entity.PublicEntity;
import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.commons.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractCrudService<D extends AbstractPublicDTO, E extends PublicEntity> implements CrudService<D> {


    private final CrudMapper<D, E> mapper;
    private final PublicRepository<E, Long> repository;

    @Override
    public D create(D dto) {
        dto.setPublicId(generateQrCode());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public D update(UUID id, D dto) {
        return repository.findByUuidSecure(id, SecurityUtils.getPrincipalUUID()).map(entity -> {
            E updatedEntity = mapper.partialUpdate(dto, entity);
            return mapper.toDto(repository.save(updatedEntity));
        }).orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Override
    public void delete(UUID id) {
        repository.findByUuidSecure(id, SecurityUtils.getPrincipalUUID()).ifPresent(repository::delete);
    }

    @Override
    public D findById(UUID id) {
        return repository.findByUuidSecure(id, SecurityUtils.getPrincipalUUID())
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Override
    public D findByIdPublic(UUID id) {
        return repository.findByUuid(id).map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Override
    public List<D> findAll(Pageable pageable) {
        Specification<E> spec = Specification.where(
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(AbstractPublicDTO.Fields.userId), SecurityUtils.getPrincipalUUID()));
        return mapper.toDto(repository.findAll(spec, pageable));
    }

    private UUID generateQrCode() {
        return UUID.randomUUID();
    }
}
