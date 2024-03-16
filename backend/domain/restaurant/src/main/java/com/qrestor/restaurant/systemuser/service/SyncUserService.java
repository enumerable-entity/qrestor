package com.qrestor.restaurant.systemuser.service;

import com.qrestor.models.dto.RestaurantBasicInfoDTO;
import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.restaurant.systemuser.enitity.SyncUser;
import com.qrestor.restaurant.systemuser.mapper.SyncUserMapper;
import com.qrestor.restaurant.systemuser.repository.SyncUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SyncUserService {

    private final SyncUserRepository syncUserRepository;
    private final SyncUserMapper syncUserMapper;

    public void save(UserKafkaSyncDTO syncDTO) {
        syncUserRepository.saveAndFlush(syncUserMapper.toEntity(syncDTO));
    }

    public List<RestaurantBasicInfoDTO> getRestaurantIdForWaiter(UUID waiterId) {
        Optional<SyncUser> waiter = syncUserRepository.getByUuid(waiterId);
        return waiter.map(this::toRestaurantBasicInfoDTO).orElseThrow(()-> new RuntimeException("Waiter not found"));
    }

    private List<RestaurantBasicInfoDTO> toRestaurantBasicInfoDTO(SyncUser syncUser) {
        RestaurantBasicInfoDTO restaurantBasicInfoDTO = new RestaurantBasicInfoDTO();
        restaurantBasicInfoDTO.setPublicId(syncUser.getRestaurant().getPublicId());
        restaurantBasicInfoDTO.setName(syncUser.getRestaurant().getName());
        restaurantBasicInfoDTO.setTitle(syncUser.getRestaurant().getTitle());
        return List.of(restaurantBasicInfoDTO);
    }
}
