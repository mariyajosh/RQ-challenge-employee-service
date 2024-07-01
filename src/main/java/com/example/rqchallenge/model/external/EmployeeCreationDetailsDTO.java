package com.example.rqchallenge.model.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class EmployeeCreationDetailsDTO {
    private String status;
}
