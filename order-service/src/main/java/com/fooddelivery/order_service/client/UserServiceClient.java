package com.fooddelivery.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fooddelivery.order_service.dto.ApiResponse;
import com.fooddelivery.order_service.dto.UserResponseDTO;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/users/{id}")
    ApiResponse<UserResponseDTO> getUserById(@PathVariable("id") Long id);
}
