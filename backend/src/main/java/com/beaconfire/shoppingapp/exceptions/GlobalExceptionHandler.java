package com.beaconfire.shoppingapp.exceptions;

import com.beaconfire.shoppingapp.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {NotEnoughInventoryException.class})
    public ResponseEntity<ResponseDto<String>> handleNotEnoughInventoryException(NotEnoughInventoryException e){
        return ResponseDto.<String>get(null, e.getMessage(), HttpStatus.NOT_ACCEPTABLE).toResponseEntity();
    }

    @ExceptionHandler(value = {InvalidCredentialsException.class})
    public ResponseEntity<ResponseDto<String>> handleInvalidCredentialsException(InvalidCredentialsException e){
        return ResponseDto.<String>get(null, e.getMessage(), HttpStatus.UNAUTHORIZED).toResponseEntity();
    }

    @ExceptionHandler(value = {NotHaveAccessException.class})
    public ResponseEntity<ResponseDto<String>> handleNotHaveAccessException(NotHaveAccessException e){
        return ResponseDto.<String>get(null, e.getMessage(), HttpStatus.FORBIDDEN).toResponseEntity();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
