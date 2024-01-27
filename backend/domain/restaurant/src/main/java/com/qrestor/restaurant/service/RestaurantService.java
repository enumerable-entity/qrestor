package com.qrestor.restaurant.service;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.CrudService;
import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.restaurant.api.dto.RestaurantDTO;
import com.qrestor.restaurant.entity.RestaurantEntity;
import com.qrestor.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService extends AbstractCrudService<RestaurantDTO, RestaurantEntity> implements CrudService<RestaurantDTO> {
    public RestaurantService(CrudMapper<RestaurantDTO, RestaurantEntity> mapper, RestaurantRepository repository) {
        super(mapper, repository);
    }
}
