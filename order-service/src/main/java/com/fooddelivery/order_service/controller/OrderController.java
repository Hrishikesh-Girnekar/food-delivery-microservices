package com.fooddelivery.order_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.order_service.dto.ApiResponse;
import com.fooddelivery.order_service.dto.OrderDetailsResponseDTO;
import com.fooddelivery.order_service.dto.OrderRequestDTO;
import com.fooddelivery.order_service.dto.OrderResponseDTO;
import com.fooddelivery.order_service.dto.UpdateOrderStatusRequestDTO;
import com.fooddelivery.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(@RequestBody OrderRequestDTO requestDTO) {

		OrderResponseDTO order = orderService.createOrder(requestDTO);

		ApiResponse<OrderResponseDTO> response = ApiResponse.<OrderResponseDTO>builder().success(true)
				.message("Order created successfully").data(order).build();

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrderById(@PathVariable Long id) {

		OrderResponseDTO order = orderService.getOrderById(id);

		ApiResponse<OrderResponseDTO> response = ApiResponse.<OrderResponseDTO>builder().success(true)
				.message("Order fetched successfully").data(order).build();

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrders() {

		List<OrderResponseDTO> orders = orderService.getAllOrders();

		ApiResponse<List<OrderResponseDTO>> response = ApiResponse.<List<OrderResponseDTO>>builder().success(true)
				.message("Orders fetched successfully").data(orders).build();

		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getOrdersByUserId(@PathVariable Long userId) {

	    List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);

	    ApiResponse<List<OrderResponseDTO>> response =
	            ApiResponse.<List<OrderResponseDTO>>builder()
	                    .success(true)
	                    .message("Orders fetched successfully for user")
	                    .data(orders)
	                    .build();

	    return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrderStatus(
	        @PathVariable Long id,
	        @RequestBody UpdateOrderStatusRequestDTO requestDTO) {

	    OrderResponseDTO order = orderService.updateOrderStatus(id, requestDTO);

	    ApiResponse<OrderResponseDTO> response =
	            ApiResponse.<OrderResponseDTO>builder()
	                    .success(true)
	                    .message("Order status updated successfully")
	                    .data(order)
	                    .build();

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}/details")
	public ResponseEntity<ApiResponse<OrderDetailsResponseDTO>> getOrderDetails(@PathVariable Long id) {

	    OrderDetailsResponseDTO details = orderService.getOrderDetails(id);

	    ApiResponse<OrderDetailsResponseDTO> response =
	            ApiResponse.<OrderDetailsResponseDTO>builder()
	                    .success(true)
	                    .message("Order details fetched successfully")
	                    .data(details)
	                    .build();

	    return ResponseEntity.ok(response);
	}
}