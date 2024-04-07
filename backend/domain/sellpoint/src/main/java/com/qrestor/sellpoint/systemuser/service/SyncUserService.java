package com.qrestor.sellpoint.systemuser.service;

import com.qrestor.models.dto.RestaurantBasicInfoDTO;
import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.sellpoint.systemuser.enitity.SyncUser;
import com.qrestor.sellpoint.systemuser.mapper.SyncUserMapper;
import com.qrestor.sellpoint.systemuser.repository.SyncUserRepository;
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
        return waiter.map(this::toRestaurantBasicInfoDTO).orElseThrow(() -> new RuntimeException("Waiter not found"));
    }

    private List<RestaurantBasicInfoDTO> toRestaurantBasicInfoDTO(SyncUser syncUser) {
        RestaurantBasicInfoDTO restaurantBasicInfoDTO = new RestaurantBasicInfoDTO();
        restaurantBasicInfoDTO.setPublicId(syncUser.getSellingPoint().getPublicId());
        restaurantBasicInfoDTO.setName(syncUser.getSellingPoint().getName());
        restaurantBasicInfoDTO.setTitle(syncUser.getSellingPoint().getTitle());
        return List.of(restaurantBasicInfoDTO);
    }
}
