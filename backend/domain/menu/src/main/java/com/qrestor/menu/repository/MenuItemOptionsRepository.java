package com.qrestor.menu.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.menu.entity.MenuItemOptionEntity;
import com.qrestor.models.dto.DictionaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuItemOptionsRepository extends PublicRepository<MenuItemOptionEntity, Long> {

    @Query("""
            select new com.qrestor.models.dto.DictionaryDTO(o.publicId, o.title)
            from MenuItemOptionEntity o
            where o.isEnabled = true
            and o.userId = :userId
            and (cast(:menuItemId as uuid) is null OR o.menuItem.publicId = :menuItemId)
            """)
    Collection<DictionaryDTO<String>> getMenuItemOptionsCombo(UUID userId, @Nullable UUID menuItemId);

    Optional<MenuItemOptionEntity> findByUserIdAndPublicId(UUID userId, UUID publicId);

    @EntityGraph(attributePaths = {"menuItem", "menuItemOptionPositions"})
    @Override
    Page<MenuItemOptionEntity> findAll(Specification<MenuItemOptionEntity> spec, Pageable pageable);
}
