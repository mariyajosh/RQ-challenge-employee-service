package com.example.rqchallenge.model.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class EmployeeCreationDetailsDTO {
    private String status;
}
