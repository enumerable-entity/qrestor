package com.qrestor.menu.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.menu.entity.ItemCategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CategoryRepository extends PublicRepository<ItemCategoryEntity, Long> {

    @Query("""
            select new com.qrestor.models.dto.DictionaryDTO(r.publicId, r.nlsKey)
            from ItemCategoryEntity r
            where r.isEnabled = true
            """)
    Collection<DictionaryDTO<String>> getCategoryCombo();
}
