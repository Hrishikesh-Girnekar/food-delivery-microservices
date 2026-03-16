package com.fooddelivery.order_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.fooddelivery.order_service.dto.OrderRequestDTO;
import com.fooddelivery.order_service.dto.OrderResponseDTO;
import com.fooddelivery.order_service.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderRequestDTO dto);

    OrderResponseDTO toResponseDto(Order order);

    List<OrderResponseDTO> toResponseDtoList(List<Order> orders);
}