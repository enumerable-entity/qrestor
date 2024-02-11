package com.qrestor.orders.mapper;

import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.orders.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderDTO toDto(OrderEntity orderEntity);

    List<OrderDTO> toDto(List<OrderEntity> orderEntityList);

    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "completedDate", ignore = true)
    @Mapping(target = "cancelledDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    OrderEntity toEntity(OrderDTO orderDTO);

}
