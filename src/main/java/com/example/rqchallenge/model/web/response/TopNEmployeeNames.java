package com.example.rqchallenge.model.web.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TopNEmployeeNames {
    private List<EmployeeName> employeeNameList;
}
