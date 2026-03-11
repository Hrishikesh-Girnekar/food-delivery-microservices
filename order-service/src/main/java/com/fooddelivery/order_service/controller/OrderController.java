package com.fooddelivery.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.order_service.client.UserClient;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final UserClient userClient;

    @GetMapping("/test")
    public String testOrder() {

        String response = userClient.getUserTest();

        return "Order Service -> " + response;
    }
}
