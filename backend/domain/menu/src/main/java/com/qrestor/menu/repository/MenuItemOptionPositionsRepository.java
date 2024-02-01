package com.qrestor.menu.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.commons.dto.DictionaryDTO;
import com.qrestor.menu.entity.MenuItemOptionPositionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface MenuItemOptionPositionsRepository extends PublicRepository<MenuItemOptionPositionEntity, Long> {

    @Query("""
            select new com.qrestor.commons.dto.DictionaryDTO(o.publicId, o.title)
            from MenuItemOptionPositionEntity o
            where o.isEnabled = true
            and o.userId = :userId
            and (cast(:menuItemOptionId as uuid) is null OR o.menuItemOption.publicId = :menuItemOptionId)
            """)
    Collection<DictionaryDTO<String>> getMenuItemOptionsPositionsCombo(UUID userId, @Nullable UUID menuItemOptionId);
}
