package com.qrestor.restaurant.service;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.CrudService;
import com.qrestor.models.dto.PermissionCheckResponse;
import com.qrestor.restaurant.api.dto.RestaurantDTO;
import com.qrestor.restaurant.entity.RestaurantEntity;
import com.qrestor.restaurant.mapper.RestaurantMapper;
import com.qrestor.restaurant.repository.RestaurantRepository;
import com.qrestor.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestaurantService extends AbstractCrudService<RestaurantDTO, RestaurantEntity> implements CrudService<RestaurantDTO> {
    public RestaurantService(RestaurantMapper mapper, RestaurantRepository repository) {
        super(mapper, repository);
    }

    public PermissionCheckResponse checkOwnership(UUID restaurantId) {
        boolean exist = ((RestaurantRepository) repository).existsByPublicIdAndUserId(restaurantId, SecurityUtils.getPrincipalUUID());
        return new PermissionCheckResponse(exist);
    }

    public UUID getRestaurantOwnerId(UUID restaurantId) {
        return repository.findByUuid(restaurantId)
                .map(RestaurantEntity::getUserId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }
}
