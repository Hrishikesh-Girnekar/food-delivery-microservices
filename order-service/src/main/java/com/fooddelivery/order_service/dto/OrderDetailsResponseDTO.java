package com.fooddelivery.order_service.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsResponseDTO {

    private OrderResponseDTO order;

    private UserResponseDTO user;
}
