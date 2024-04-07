package com.qrestor.menu.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.menu.entity.MenuEntity;
import com.qrestor.models.dto.DictionaryDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuRepository extends PublicRepository<MenuEntity, Long> {

    @EntityGraph(attributePaths = {"menuItems.itemCategory", "menuItems.ingredients"})
    Optional<MenuEntity> findActiveMenuEntityByRestaurantIdAndIsActiveTrue(UUID restaurantId);


    @EntityGraph(attributePaths = {"menuItems.itemCategory", "menuItems.menuItemOptions"})
    Collection<MenuEntity> findAllByUserIdAndRestaurantId(UUID userId,
                                                          UUID restaurantId);

    @Query("""
            select new com.qrestor.models.dto.DictionaryDTO(o.publicId, o.name)
            from MenuEntity o
            where o.isActive = true
            and o.userId = :userId
            """)
    Collection<DictionaryDTO<String>> getMenuCombo(UUID userId);
}