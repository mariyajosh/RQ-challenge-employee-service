package com.example.rqchallenge.model.external;

import com.example.rqchallenge.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO {
    private int id;
    @JsonProperty(value = "employee_name")
    private String name;
    @JsonProperty(value = "employee_age")
    private int age;
    @JsonProperty(value = "employee_salary")
    private int salary;

    public Employee toEmployee(){
        return new Employee(this.id, this.name, this.age, this.salary);
    }
}
