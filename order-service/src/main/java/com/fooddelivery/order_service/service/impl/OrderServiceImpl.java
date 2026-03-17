package com.fooddelivery.order_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.order_service.client.UserServiceClient;
import com.fooddelivery.order_service.dto.ApiResponse;
import com.fooddelivery.order_service.dto.OrderDetailsResponseDTO;
import com.fooddelivery.order_service.dto.OrderRequestDTO;
import com.fooddelivery.order_service.dto.OrderResponseDTO;
import com.fooddelivery.order_service.dto.UpdateOrderStatusRequestDTO;
import com.fooddelivery.order_service.dto.UserResponseDTO;
import com.fooddelivery.order_service.entity.Order;
import com.fooddelivery.order_service.enums.OrderStatus;
import com.fooddelivery.order_service.exception.OrderNotFoundException;
import com.fooddelivery.order_service.mapper.OrderMapper;
import com.fooddelivery.order_service.repository.OrderRepository;
import com.fooddelivery.order_service.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final UserServiceClient userServiceClient;
	private final OrderMapper orderMapper;

	@Override
	@CircuitBreaker(name = "userService", fallbackMethod = "userServiceFallback")
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

	public OrderResponseDTO userServiceFallback(OrderRequestDTO requestDTO, Throwable throwable) {

		throw new RuntimeException("User service is currently unavailable. Please try again later.");
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

	@Override
	public OrderResponseDTO updateOrderStatus(Long id, UpdateOrderStatusRequestDTO requestDTO) {

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

		order.setStatus(requestDTO.getStatus());

		Order updatedOrder = orderRepository.save(order);

		return orderMapper.toResponseDto(updatedOrder);
	}

	@Override
	@CircuitBreaker(name = "userService", fallbackMethod = "orderDetailsFallback")
	public OrderDetailsResponseDTO getOrderDetails(Long orderId) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

		ApiResponse<UserResponseDTO> userResponse;

		try {
			userResponse = userServiceClient.getUserById(order.getUserId());
		} catch (Exception ex) {
			throw new RuntimeException("Unable to fetch user details");
		}

		OrderResponseDTO orderDTO = orderMapper.toResponseDto(order);

		return OrderDetailsResponseDTO.builder().order(orderDTO).user(userResponse.getData()).build();
	}

	public OrderDetailsResponseDTO orderDetailsFallback(Long orderId, Throwable throwable) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

		return OrderDetailsResponseDTO.builder().order(orderMapper.toResponseDto(order)).user(null) // user data unavailable
				.build();
	}
}