package com.example.rqchallenge.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TopNEmployeeNames {
    private List<EmployeeName> employeeNameList;
}
