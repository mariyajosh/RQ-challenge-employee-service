package com.example.rqchallenge.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllApiResponse {
    List<EmployeeDetails> employees;
}
