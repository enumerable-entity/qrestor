package com.qrestor.commons;

import com.qrestor.commons.dto.AbstractPublicDTO;
import com.qrestor.commons.entity.PublicEntity;
import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.commons.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.qrestor.commons.common.Constants.USER_PROPERTY;

@RequiredArgsConstructor
public abstract class AbstractCrudService<D extends AbstractPublicDTO, E extends PublicEntity> implements CrudService<D> {


    protected final CrudMapper<D, E> mapper;
    protected final PublicRepository<E, Long> repository;

    protected final Specification<E> OWNER_SPEC = (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get(USER_PROPERTY), SecurityUtils.getPrincipalUUID());

    @Override
    @Transactional
    public D create(D dto) {
        dto.setPublicId(generateQrCode());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public D update(D dto) {
        return repository.findByUuidSecure(dto.getPublicId(), SecurityUtils.getPrincipalUUID()).map(entity -> {
            E updatedEntity = mapper.partialUpdate(dto, entity);
            return mapper.toDto(repository.save(updatedEntity));
        }).orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Override
    public void delete(UUID id) {
        repository.findByUuidSecure(id, SecurityUtils.getPrincipalUUID()).ifPresent(repository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public D findById(UUID id) {
        return repository.findByUuidSecure(id, SecurityUtils.getPrincipalUUID())
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public D findByIdPublic(UUID id) {
        return repository.findByUuid(id).map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<D> findAll(Pageable pageable) {
        Specification<E> spec = Specification.where(OWNER_SPEC);
        return mapper.toDto(repository.findAll(spec, pageable));
    }

    /**
     * Find all entities without security check and pagination.
     * WARNING: Use this method only for small public available datasets.
     */
    @Override
    @Transactional(readOnly = true)
    public List<D> findTotallyAll() {
        return mapper.toDto(repository.findAll());
    }

    protected UUID generateQrCode() {
        return UUID.randomUUID();
    }
}
