package com.fooddelivery.user_service.exception;



import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fooddelivery.user_service.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException ex) {

	    ApiResponse<Object> response = ApiResponse.builder()
	            .success(false)
	            .message(ex.getMessage())
	            .data(null)
	            .build();

	    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
