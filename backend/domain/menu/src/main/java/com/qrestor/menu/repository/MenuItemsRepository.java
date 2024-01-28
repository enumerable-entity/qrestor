package com.qrestor.menu.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.menu.entity.MenuItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemsRepository extends PublicRepository<MenuItemEntity, Long> {

    @EntityGraph(attributePaths = {"itemCategory", "ingredients"})
    @Override
    Page<MenuItemEntity> findAll(Specification<MenuItemEntity> spec, Pageable pageable);
}