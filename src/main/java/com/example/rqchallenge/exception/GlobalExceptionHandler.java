package com.example.rqchallenge.exception;

import com.example.rqchallenge.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{
    @ExceptionHandler({ExternalServiceException.class})
    public ResponseEntity<ErrorResponse> handleExternalServiceException(ExternalServiceException exception){
        return ResponseEntity.internalServerError().body(new ErrorResponse("Something went wrong!"));
    }

    @ExceptionHandler({ExternalServiceUnavailableException.class})
    public ResponseEntity<ErrorResponse> handleExternalServiceUnavailableException(ExternalServiceUnavailableException exception){
        return ResponseEntity.internalServerError().body(new ErrorResponse("Something went wrong!"));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception){
        log.error(exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(new ErrorResponse("Something went wrong!"));
    }
}
