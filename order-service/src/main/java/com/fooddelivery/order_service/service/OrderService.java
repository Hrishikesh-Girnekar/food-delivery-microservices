package com.fooddelivery.order_service.service;



import java.util.List;

import com.fooddelivery.order_service.dto.OrderRequestDTO;
import com.fooddelivery.order_service.dto.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO requestDTO);
    
    OrderResponseDTO getOrderById(Long id);
    
    List<OrderResponseDTO> getAllOrders();
    
    List<OrderResponseDTO> getOrdersByUserId(Long userId);
    
}
