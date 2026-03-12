package com.fooddelivery.order_service.dto;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long userId;

    private String restaurantName;

    private String itemName;

    private Double price;
}