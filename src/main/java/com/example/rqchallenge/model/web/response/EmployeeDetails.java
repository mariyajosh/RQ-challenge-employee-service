package com.example.rqchallenge.model.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeDetails {
    private int id;
    private String name;
    private int age;
    private int salary;
}
