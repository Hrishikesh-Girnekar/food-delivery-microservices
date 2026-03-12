package com.fooddelivery.order_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.order_service.dto.ApiResponse;
import com.fooddelivery.order_service.dto.OrderRequestDTO;
import com.fooddelivery.order_service.dto.OrderResponseDTO;
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

        ApiResponse<OrderResponseDTO> response = ApiResponse.<OrderResponseDTO>builder()
                .success(true)
                .message("Order created successfully")
                .data(order)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}