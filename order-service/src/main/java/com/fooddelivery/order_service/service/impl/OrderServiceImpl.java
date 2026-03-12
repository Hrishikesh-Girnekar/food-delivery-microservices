package com.fooddelivery.order_service.service.impl;



import org.springframework.stereotype.Service;

import com.fooddelivery.order_service.client.UserServiceClient;
import com.fooddelivery.order_service.dto.ApiResponse;
import com.fooddelivery.order_service.dto.OrderRequestDTO;
import com.fooddelivery.order_service.dto.OrderResponseDTO;
import com.fooddelivery.order_service.dto.UserResponseDTO;
import com.fooddelivery.order_service.entity.Order;
import com.fooddelivery.order_service.enums.OrderStatus;
import com.fooddelivery.order_service.repository.OrderRepository;
import com.fooddelivery.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO) {

        ApiResponse<UserResponseDTO> userResponse;

        try {
            userResponse = userServiceClient.getUserById(requestDTO.getUserId());
        } 
        catch (Exception ex) {
            throw new RuntimeException("User not found. Cannot create order.");
        }

        if (!userResponse.isSuccess() || userResponse.getData() == null) {
            throw new RuntimeException("User not found. Cannot create order.");
        }

        Order order = Order.builder()
                .userId(requestDTO.getUserId())
                .restaurantName(requestDTO.getRestaurantName())
                .itemName(requestDTO.getItemName())
                .price(requestDTO.getPrice())
                .status(OrderStatus.CREATED)
                .build();

        Order savedOrder = orderRepository.save(order);

        return OrderResponseDTO.builder()
                .id(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .restaurantName(savedOrder.getRestaurantName())
                .itemName(savedOrder.getItemName())
                .price(savedOrder.getPrice())
                .status(savedOrder.getStatus())
                .build();
    }
}
