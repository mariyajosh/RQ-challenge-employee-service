package com.example.rqchallenge.model;

import com.example.rqchallenge.model.response.EmployeeDetails;
import com.example.rqchallenge.model.response.GetAllApiResponse;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Employees {
    private List<Employee> employeeList;

    public GetAllApiResponse toGetAllAPIResponse() {
        List<EmployeeDetails> employeeDetailsList = employeeList.stream()
                .map(Employee::toEmployeeDetails)
                .collect(Collectors.toList());
        return new GetAllApiResponse(employeeDetailsList);
    }

}
