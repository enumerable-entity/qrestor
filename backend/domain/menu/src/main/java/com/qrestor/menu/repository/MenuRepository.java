package com.qrestor.menu.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.menu.entity.MenuEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuRepository extends PublicRepository<MenuEntity, Long> {

    @EntityGraph(attributePaths = {"menuItems.itemCategory", "menuItems.ingredients"})
    Optional<MenuEntity> findActiveMenuEntityByRestaurantIdAndIsActiveTrue(UUID restaurantId);
}