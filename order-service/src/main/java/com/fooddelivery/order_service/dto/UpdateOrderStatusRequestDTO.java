package com.fooddelivery.order_service.dto;

import com.fooddelivery.order_service.enums.OrderStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderStatusRequestDTO {

    private OrderStatus status;
}