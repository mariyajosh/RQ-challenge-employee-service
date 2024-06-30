package com.example.rqchallenge.model;

import com.example.rqchallenge.model.response.EmployeeDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Employee {
    private int id;
    private String name;
    private int age;
    private int salary;

    public EmployeeDetails toEmployeeDetails(){
        return new EmployeeDetails(this.id, this.name, this.age, this.salary);
    }
}
