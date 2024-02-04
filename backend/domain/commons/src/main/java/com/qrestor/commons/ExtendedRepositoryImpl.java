package com.qrestor.commons;

import com.qrestor.models.dto.AbstractPublicDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.Optional;
import java.util.UUID;

import static com.qrestor.commons.common.Constants.USER_PROPERTY;

public class ExtendedRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements PublicRepository<T, ID> {

    private final EntityManager entityManager;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> information, EntityManager entityManager) {
        super(information, entityManager);
        this.entityManager = entityManager;
    }

    @Transactional
    public Optional<T> findByUuid(UUID uuid) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
        Root<T> root = cQuery.from(getDomainClass());
        cQuery.select(root).where(builder.equal(root.get(AbstractPublicDTO.Fields.publicId), uuid));
        TypedQuery<T> query = entityManager.createQuery(cQuery);
        return handleNoResult(query);
    }

    @Transactional
    public Optional<T> findByUuidSecure(UUID uuid, UUID userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
        Root<T> root = cQuery.from(getDomainClass());
        cQuery.select(root).where(builder.equal(root.get(AbstractPublicDTO.Fields.publicId), uuid),
                builder.equal(root.get("userId"), userId));
        TypedQuery<T> query = entityManager.createQuery(cQuery);
        return handleNoResult(query);
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid, UUID userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete = builder.createCriteriaDelete(getDomainClass());
        Root<T> root = criteriaDelete.from(getDomainClass());
        criteriaDelete.where(builder.equal(root.get(AbstractPublicDTO.Fields.publicId), uuid),
                builder.equal(root.get(USER_PROPERTY), userId));
        entityManager.createQuery(criteriaDelete).executeUpdate();
    }


    private Optional<T> handleNoResult(TypedQuery<T> query) {
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
