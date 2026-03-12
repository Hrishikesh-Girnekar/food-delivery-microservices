package com.fooddelivery.order_service.entity;



import com.fooddelivery.order_service.enums.OrderStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
