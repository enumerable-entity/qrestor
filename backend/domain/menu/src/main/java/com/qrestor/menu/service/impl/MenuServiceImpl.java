package com.qrestor.menu.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.Utils;
import com.qrestor.menu.api.dto.MenuDTO;
import com.qrestor.menu.api.dto.list.MenuListDTO;
import com.qrestor.menu.entity.AggregatedMenuDocumentEntity;
import com.qrestor.menu.entity.MenuEntity;
import com.qrestor.menu.mapper.MenuMapper;
import com.qrestor.menu.mapper.PublicMenuMapper;
import com.qrestor.menu.mapper.export.MenuExportMapper;
import com.qrestor.menu.repository.MenuRepository;
import com.qrestor.menu.repository.mongo.AggregateMenuRepository;
import com.qrestor.menu.service.MenuService;
import com.qrestor.menu.validator.MenuValidator;
import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.models.dto.menu.eximport.MenuAgregateDTO;
import com.qrestor.security.SecurityUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuServiceImpl extends AbstractCrudService<MenuDTO, MenuEntity> implements MenuService {
    private final MenuValidator validator;
    private final MenuExportMapper exportMapper;
    private final MenuRepository repository;
    private final AggregateMenuRepository aggregateMenuRepository;
    private final PublicMenuMapper publicMenuMapper;
    private final ObjectMapper objectMapper;

    public MenuServiceImpl(MenuMapper mapper,
                           MenuRepository repository,
                           MenuValidator validator,
                           MenuExportMapper exportMapper,
                           AggregateMenuRepository aggregateMenuRepository,
                           PublicMenuMapper publicMenuMapper,
                           ObjectMapper objectMapper) {
        super(mapper, repository);
        this.validator = validator;
        this.exportMapper = exportMapper;
        this.repository = repository;
        this.aggregateMenuRepository = aggregateMenuRepository;
        this.publicMenuMapper = publicMenuMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public MenuDTO create(MenuDTO dto) {
        validator.validateAdd(dto);
        dto.setPublicId(Utils.generatePublicId());
        MenuEntity savedEntity = repository.save(mapper.toEntity(dto));
        List<MenuListDTO> aggregateObject = publicMenuMapper.toAggregateObject(savedEntity);
        Optional<MenuEntity> fetchedEntity = repository.findById(savedEntity.getId());
        saveDocument(aggregateObject, fetchedEntity.get().getRestaurantId());
        return mapper.toDto(savedEntity);
    }

    @SneakyThrows
    private void saveDocument(List<MenuListDTO> aggregateObject,
                              UUID restaurantId) {
        aggregateMenuRepository.save(new AggregatedMenuDocumentEntity(restaurantId, aggregateObject));
    }

    @Override
    public Optional<MenuEntity> findEntityByUuid(UUID uuid) {
        return repository.findByUuidSecure(uuid, SecurityUtils.getPrincipalUUID());
    }

    @Override
    @Transactional(readOnly = true)
    public MenuAgregateDTO getMenuAggregateForRestaurant(UUID restaurantId) {
        Collection<MenuEntity> allRestaurantMenus = repository.findAllByUserIdAndRestaurantId(SecurityUtils.getPrincipalUUID(), restaurantId);
        if (!allRestaurantMenus.isEmpty()) {
            MenuAgregateDTO menuAgregateDTO = new MenuAgregateDTO();
            menuAgregateDTO.setMenus(exportMapper.toDTOs(allRestaurantMenus));
            return menuAgregateDTO;
        }
        return new MenuAgregateDTO();
    }

    @Override
    public Collection<DictionaryDTO<String>> getMenuCombo() {
        return repository.getMenuCombo(SecurityUtils.getPrincipalUUID());
    }
}
