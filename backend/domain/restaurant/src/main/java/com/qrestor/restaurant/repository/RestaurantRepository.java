package com.qrestor.restaurant.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.restaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends PublicRepository<RestaurantEntity, Long> {

    @EntityGraph(attributePaths = {"settings", "address"})
    @Override
    Page<RestaurantEntity> findAll(Specification<RestaurantEntity> specification, Pageable pageable);

    @Query("""
            select new com.qrestor.models.dto.DictionaryDTO(r.publicId, r.name)
            from RestaurantEntity r
            where r.userId = :principalUUID
            """)
    Collection<DictionaryDTO<String>> getRestaurantCombo(UUID principalUUID);

    boolean existsByPublicIdAndUserId(UUID publicId, UUID userId);

    List<RestaurantEntity> findAllByUserUuid(UUID userUuid);
}