package com.pratice_project.handlingException;

import com.pratice_project.ApiResponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResouceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResouceNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>( ex.getMessage(), HttpStatus.NOT_FOUND.value(), null),
                HttpStatus.NOT_FOUND
        );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                new ApiResponse<>("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    }


