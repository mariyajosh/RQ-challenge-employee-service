package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.web.request.CreateEmployeeRequest;
import com.example.rqchallenge.model.web.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;


public interface IEmployeeController {

    @GetMapping("/employees")
    ResponseEntity<GetAllEmployeeApiResponse> getAllEmployees() throws IOException;

    @GetMapping("/employees/search/{searchString}")
    ResponseEntity<GetAllEmployeeApiResponse> getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping("/employees/{id}")
    ResponseEntity<GetEmployeeByIdResponse> getEmployeeById(@PathVariable String id);

    @GetMapping("/employees/highestSalary")
    ResponseEntity<HighestSalaryOfAllEmployee> getHighestSalaryOfEmployees();

    @GetMapping("/employees/topTenHighestEarningEmployeeNames")
    ResponseEntity<TopNEmployeeNames> getTopTenHighestEarningEmployeeNames();

    @PostMapping("/employee")
    ResponseEntity<CreateEmployeeResponse> createEmployee( @Valid @RequestBody CreateEmployeeRequest createEmployeeRequest);

    @DeleteMapping("/employees/{id}")
    ResponseEntity<DeleteEmployeeResponse> deleteEmployeeById(@PathVariable String id);

}
