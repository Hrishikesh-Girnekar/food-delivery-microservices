package com.fooddelivery.user_service.service;

import java.util.List;

import com.fooddelivery.user_service.dto.UserRequestDTO;
import com.fooddelivery.user_service.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO requestDTO);
    
    UserResponseDTO getUserById(Long userId);
    
    List<UserResponseDTO> getAllUsers();
}