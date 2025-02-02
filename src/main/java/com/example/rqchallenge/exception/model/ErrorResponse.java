package com.example.rqchallenge.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private List<ErrorDetails> errorDetails;
}
