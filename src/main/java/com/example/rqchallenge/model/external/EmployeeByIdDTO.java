package com.example.rqchallenge.model.external;

import com.example.rqchallenge.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeByIdDTO {

    @JsonProperty(value = "data")
    private EmployeeDTO employeeDTO;

    public Employee toEmployee(){
        return this.employeeDTO.toEmployee();
    }
}
