package com.qrestor.resolverqr.service;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.CrudService;
import com.qrestor.commons.PublicRepository;
import com.qrestor.commons.Utils;
import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.resolverqr.api.dto.QrCodeMappingDTO;
import com.qrestor.resolverqr.client.MenuServiceClient;
import com.qrestor.resolverqr.client.RestaurantHttpClient;
import com.qrestor.resolverqr.entity.QrCodeMappingEntity;
import com.qrestor.security.SecurityUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QrManagementService extends AbstractCrudService<QrCodeMappingDTO, QrCodeMappingEntity>
        implements CrudService<QrCodeMappingDTO> {

    private final RestaurantHttpClient restaurantHttpClient;
    private final MenuServiceClient menuServiceClient;

    public QrManagementService(
            CrudMapper<QrCodeMappingDTO,
                    QrCodeMappingEntity> mapper,
            PublicRepository<QrCodeMappingEntity, Long> repository,
            RestaurantHttpClient restaurantHttpClient,
            MenuServiceClient menuServiceClient) {
        super(mapper, repository);
        this.restaurantHttpClient = restaurantHttpClient;
        this.menuServiceClient = menuServiceClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<QrCodeMappingDTO> findAll(Pageable pageable) {
        Specification<QrCodeMappingEntity> spec = Specification.where(OWNER_SPEC);
        List<QrCodeMappingDTO> dto = mapper.toDto(repository.findAll(spec, pageable));
        Map<UUID, String> restaurantsMap = getSellingPointMap(restaurantHttpClient.getRestaurantsDict());
        Map<UUID, String> menuMap = getSellingPointMap(menuServiceClient.getMenuCombo());

        dto.forEach(d -> {
            d.setRestaurantName(restaurantsMap.get(d.getSellingPointId()));
            d.setMenuName(menuMap.get(d.getMenuId()));
        });
        return dto;
    }

    @Override
    @Transactional
    public QrCodeMappingDTO create(QrCodeMappingDTO dto) {
        dto.setPublicId(Utils.generatePublicId());
        QrCodeMappingDTO response = mapper.toDto(repository.save(mapper.toEntity(dto)));
        return fillTitles(response);
    }

    @Override
    @Transactional
    public QrCodeMappingDTO update(QrCodeMappingDTO dto) {
        return repository.findByUuidSecure(dto.getPublicId(), SecurityUtils.getPrincipalUUID()).map(entity -> {
            QrCodeMappingEntity updatedEntity = mapper.partialUpdate(dto, entity);
            QrCodeMappingDTO response = mapper.toDto(repository.save(updatedEntity));
            return fillTitles(response);
        }).orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public QrCodeMappingDTO findById(UUID id) {
        return repository.findByUuidSecure(id, SecurityUtils.getPrincipalUUID())
                .map(mapper::toDto)
                .map(this::fillTitles)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }


    private QrCodeMappingDTO fillTitles(QrCodeMappingDTO response) {
        Map<UUID, String> restaurantsMap = getSellingPointMap(restaurantHttpClient.getRestaurantsDict());
        Map<UUID, String> menuMap = getSellingPointMap(menuServiceClient.getMenuCombo());
        response.setRestaurantName(restaurantsMap.get(response.getSellingPointId()));
        response.setMenuName(menuMap.get(response.getMenuId()));
        return response;
    }

    private Map<UUID, String> getSellingPointMap(Collection<DictionaryDTO<String>> restaurantHttpClient) {
        return restaurantHttpClient
                .stream().collect(Collectors.toMap(DictionaryDTO::id, DictionaryDTO::name));
    }
}
