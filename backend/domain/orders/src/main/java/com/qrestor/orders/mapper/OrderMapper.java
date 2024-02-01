package com.qrestor.orders.mapper;

import com.qrestor.orders.api.dto.OrderDTO;
import com.qrestor.orders.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {

    OrderDTO toDto(OrderEntity orderEntity);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    OrderEntity toEntity(OrderDTO orderDTO);

}
