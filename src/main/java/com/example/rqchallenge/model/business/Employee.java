package com.example.rqchallenge.model.business;

import com.example.rqchallenge.model.web.response.EmployeeDetails;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Employee {
    private int id;
    private String name;
    private int age;
    private int salary;

    public EmployeeDetails toEmployeeDetails(){
        return new EmployeeDetails(this.id, this.name, this.age, this.salary);
    }
}
