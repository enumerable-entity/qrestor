package com.qrestor.restaurant.service;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.CrudService;
import com.qrestor.models.dto.PermissionCheckResponse;
import com.qrestor.models.dto.RestaurantBasicInfoDTO;
import com.qrestor.restaurant.api.dto.RestaurantDTO;
import com.qrestor.restaurant.entity.SellingPointEntity;
import com.qrestor.restaurant.mapper.RestaurantMapper;
import com.qrestor.restaurant.repository.RestaurantRepository;
import com.qrestor.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService extends AbstractCrudService<RestaurantDTO, SellingPointEntity> implements CrudService<RestaurantDTO> {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantMapper mapper,
                             RestaurantRepository repository) {
        super(mapper, repository);
        this.restaurantRepository = repository;
    }

    public PermissionCheckResponse checkOwnership(UUID restaurantId) {
        boolean exist = ((RestaurantRepository) repository).existsByPublicIdAndUserId(restaurantId, SecurityUtils.getPrincipalUUID());
        return new PermissionCheckResponse(exist);
    }

    public UUID getRestaurantOwnerId(UUID restaurantId) {
        return repository.findByUuid(restaurantId)
                .map(SellingPointEntity::getUserId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    public List<RestaurantBasicInfoDTO> getRestaurantsInfoForUser() {
        return restaurantRepository.findAllByUserUuid(SecurityUtils.getPrincipalUUID())
                .stream()
                .map(this::toBasicInfoDTO)
                .toList();
    }

    private RestaurantBasicInfoDTO toBasicInfoDTO(SellingPointEntity sellingPointEntity) {
        RestaurantBasicInfoDTO restaurantBasicInfoDTO = new RestaurantBasicInfoDTO();
        restaurantBasicInfoDTO.setPublicId(sellingPointEntity.getPublicId());
        restaurantBasicInfoDTO.setName(sellingPointEntity.getName());
        restaurantBasicInfoDTO.setTitle(sellingPointEntity.getTitle());
        return restaurantBasicInfoDTO;
    }
}
