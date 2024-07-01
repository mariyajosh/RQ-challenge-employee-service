package com.example.rqchallenge.model.business;

import com.example.rqchallenge.model.web.response.EmployeeDetails;
import lombok.*;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@NoArgsConstructor
    public class Employee {
    private String id;
    private String name;
    private Integer age;
private Integer salary;

    public EmployeeDetails toEmployeeDetails(){
        return new EmployeeDetails(this.id, this.name, this.age, this.salary);
    }
}
