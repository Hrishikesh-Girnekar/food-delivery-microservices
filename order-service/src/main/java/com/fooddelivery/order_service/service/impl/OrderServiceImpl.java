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
		} catch (Exception ex) {
			throw new RuntimeException("User not found. Cannot create order.");
		}

		if (!userResponse.isSuccess() || userResponse.getData() == null) {
			throw new RuntimeException("User not found. Cannot create order.");
		}

		Order order = Order.builder().userId(requestDTO.getUserId()).restaurantName(requestDTO.getRestaurantName())
				.itemName(requestDTO.getItemName()).price(requestDTO.getPrice()).status(OrderStatus.CREATED).build();

		Order savedOrder = orderRepository.save(order);

		return OrderResponseDTO.builder().id(savedOrder.getId()).userId(savedOrder.getUserId())
				.restaurantName(savedOrder.getRestaurantName()).itemName(savedOrder.getItemName())
				.price(savedOrder.getPrice()).status(savedOrder.getStatus()).build();
	}

	@Override
	public OrderResponseDTO getOrderById(Long id) {

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

		return OrderResponseDTO.builder().id(order.getId()).userId(order.getUserId())
				.restaurantName(order.getRestaurantName()).itemName(order.getItemName()).price(order.getPrice())
				.status(order.getStatus()).build();
	}

	@Override
	public List<OrderResponseDTO> getAllOrders() {

		List<Order> orders = orderRepository.findAll();

		return orders.stream()
				.map(order -> OrderResponseDTO.builder().id(order.getId()).userId(order.getUserId())
						.restaurantName(order.getRestaurantName()).itemName(order.getItemName()).price(order.getPrice())
						.status(order.getStatus()).build())
				.toList();
	}
	
	@Override
	public List<OrderResponseDTO> getOrdersByUserId(Long userId) {

	    List<Order> orders = orderRepository.findByUserId(userId);

	    return orders.stream()
	            .map(order -> OrderResponseDTO.builder()
	                    .id(order.getId())
	                    .userId(order.getUserId())
	                    .restaurantName(order.getRestaurantName())
	                    .itemName(order.getItemName())
	                    .price(order.getPrice())
	                    .status(order.getStatus())
	                    .build())
	            .toList();
	}
}
