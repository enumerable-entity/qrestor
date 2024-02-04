package com.qrestor.menu.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.menu.entity.IngredientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface IngredientRepository extends PublicRepository<IngredientEntity, Long> {
    @Query("""
            select new com.qrestor.models.dto.DictionaryDTO(r.publicId, r.name)
            from IngredientEntity r
            where r.isEnabled = true
            and r.userId = :userId
            """)
    Collection<DictionaryDTO<String>> getIngredientsCombo(UUID userId);

    List<IngredientEntity> findByUserIdAndPublicIdIn(UUID userId, List<UUID> publicIds);
}