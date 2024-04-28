package com.qrestor.menu.service.impl;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.menu.api.dto.IngredientDTO;
import com.qrestor.menu.entity.IngredientEntity;
import com.qrestor.menu.mapper.IngredientMapper;
import com.qrestor.menu.repository.IngredientRepository;
import com.qrestor.menu.service.IngredientService;
import com.qrestor.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientServiceImpl extends AbstractCrudService<IngredientDTO, IngredientEntity>
        implements IngredientService {
    public IngredientServiceImpl(IngredientMapper mapper,
                                 IngredientRepository repository) {
        super(mapper, repository);
    }

    @Override
    public List<IngredientEntity> findEntityByUuidIn(List<UUID> uuids) {
        return ((IngredientRepository) repository).findByUserIdAndPublicIdIn(SecurityUtils.getPrincipalUUID(), uuids);
    }
}
