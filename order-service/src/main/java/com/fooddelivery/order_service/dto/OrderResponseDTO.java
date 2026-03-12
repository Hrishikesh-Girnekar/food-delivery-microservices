package com.fooddelivery.order_service.dto;



import com.fooddelivery.order_service.enums.OrderStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {

    private Long id;

    private Long userId;

    private String restaurantName;

    private String itemName;

    private Double price;

    private OrderStatus status;
}
