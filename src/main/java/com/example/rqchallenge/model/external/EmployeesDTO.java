package com.example.rqchallenge.model.external;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.Employees;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class EmployeesDTO {
    @JsonProperty(value = "data")
    private List<EmployeeDTO> employees;

    public Employees toEmployees() {
        List<Employee> employeeList = employees.stream().map(EmployeeDTO::toEmployee).collect(Collectors.toList());
        return new Employees(employeeList);
    }
}
