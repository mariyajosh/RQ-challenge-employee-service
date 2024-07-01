package com.example.rqchallenge.model.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllEmployeeApiResponse {
    List<EmployeeDetails> employees;
}
