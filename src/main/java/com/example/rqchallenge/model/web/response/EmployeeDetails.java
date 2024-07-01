package com.example.rqchallenge.model.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeDetails {
    private String id;
    private String name;
    private Integer age;
    private Integer salary;
}
