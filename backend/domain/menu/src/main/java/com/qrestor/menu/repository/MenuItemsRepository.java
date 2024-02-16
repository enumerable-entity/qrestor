package com.qrestor.menu.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.menu.entity.MenuItemEntity;
import com.qrestor.menu.repository.projections.MenuItemProj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface MenuItemsRepository extends PublicRepository<MenuItemEntity, Long> {

    @EntityGraph(attributePaths = {"itemCategory", "ingredients"})
    @Override
    Page<MenuItemEntity> findAll(Specification<MenuItemEntity> spec, Pageable pageable);

    @Query("""
            select r
            from MenuItemEntity r
            where r.isEnabled = true
            and r.publicId in :publicIds
            """)
    List<MenuItemEntity> findByPublicIdIn(Set<UUID> publicIds);
}