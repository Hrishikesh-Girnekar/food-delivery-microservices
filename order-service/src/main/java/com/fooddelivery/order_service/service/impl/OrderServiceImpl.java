package com.fooddelivery.order_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.order_service.client.UserServiceClient;
import com.fooddelivery.order_service.dto.ApiResponse;
import com.fooddelivery.order_service.dto.OrderRequestDTO;
import com.fooddelivery.order_service.dto.OrderResponseDTO;
import com.fooddelivery.order_service.dto.UserResponseDTO;
import com.fooddelivery.order_service.entity.Order;
import com.fooddelivery.order_service.enums.OrderStatus;
import com.fooddelivery.order_service.exception.OrderNotFoundException;
import com.fooddelivery.order_service.mapper.OrderMapper;
import com.fooddelivery.order_service.repository.OrderRepository;
import com.fooddelivery.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO) {

        ApiResponse<UserResponseDTO> userResponse;

        try {
            userResponse = userServiceClient.getUserById(requestDTO.getUserId());
        } catch (Exception ex) {
            throw new RuntimeException("User not found. Cannot create order.");
        }

        if (!userResponse.isSuccess() || userResponse.getData() == null) {
            throw new RuntimeException("User not found. Cannot create order.");
        }

        Order order = orderMapper.toEntity(requestDTO);

        // business logic
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        return orderMapper.toResponseDto(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        return orderMapper.toResponseDtoList(orders);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        return orderMapper.toResponseDtoList(orders);
    }
}