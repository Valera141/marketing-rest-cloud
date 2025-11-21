package com.example.marketing.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    // Captura el error cuando no se encuentra un recurso (ej. 404)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(ResourceNotFoundException ex) {
        return Map.of(
            "status", 404,
            "error", ex.getMessage()
        );
    }

    // Captura los errores de validación (ej. @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        // Toma el primer error de validación y lo muestra
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        
        return Map.of(
            "status", 400,
            "error", "Validation failed",
            "message", errorMessage
        );
    }

    


}