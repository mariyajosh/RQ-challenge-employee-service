package com.example.rqchallenge.exception;

import com.example.rqchallenge.exception.model.ErrorDetails;
import com.example.rqchallenge.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.error(exception.getMessage(), exception);
        List<ErrorDetails> errorDetails = extractErrorDetails(exception);
        return ResponseEntity.badRequest().body(new ErrorResponse(errorDetails));
    }

    private static List<ErrorDetails> extractErrorDetails(MethodArgumentNotValidException exception) {
        List<FieldError> fieldError = exception.getFieldErrors();
        return fieldError.stream().
                map(error -> new ErrorDetails(error.getField() +" "+error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler({ExternalServiceException.class, ExternalServiceUnavailableException.class, Exception.class})
    public ResponseEntity<ErrorResponse> handleExternalServiceException(Exception exception){
        return ResponseEntity.internalServerError().body(new ErrorResponse(List.of(new ErrorDetails("Something went wrong!"))));
    }
}
