package com.example.rqchallenge.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler({EntityDoesNotExistException.class})
    public ResponseEntity<ErrorResponse> handleEntityDoesNotExistException(EntityDoesNotExistException exception){
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }
    @ExceptionHandler({ExternalServiceException.class})
    public ResponseEntity<ErrorResponse> handleExternalServiceException(ExternalServiceException exception){
        return ResponseEntity.internalServerError().body(new ErrorResponse("Something went wrong!"));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleGenericException(RuntimeException exception){
        return ResponseEntity.internalServerError().body(new ErrorResponse("Something went wrong!"));
    }
}
