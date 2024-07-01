package com.example.rqchallenge.model.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteEmployeeDto {
    @JsonProperty(value = "status")
    private String status;
}
