package com.example.rqchallenge.model.client;

import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
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
        if(employees == null) return new Employees();
        List<Employee> employeeList = employees.stream().map(EmployeeDTO::toEmployee).collect(Collectors.toList());
        return new Employees(employeeList);
    }
}
