package com.example.rqchallenge.model.business;

import com.example.rqchallenge.model.web.response.EmployeeDetails;
import com.example.rqchallenge.model.web.response.GetAllEmployeeApiResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Employees {
    private List<Employee> employeeList;

    public GetAllEmployeeApiResponse toGetAllAPIResponse() {
        List<EmployeeDetails> employeeDetailsList = employeeList.stream()
                .map(Employee::toEmployeeDetails)
                .collect(Collectors.toList());
        return new GetAllEmployeeApiResponse(employeeDetailsList);
    }

}
