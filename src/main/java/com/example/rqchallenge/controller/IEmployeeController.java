package com.example.rqchallenge.controller;

import com.example.rqchallenge.exception.EntityDoesNotExistException;
import com.example.rqchallenge.model.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


public interface IEmployeeController {

    @GetMapping("/employees")
    ResponseEntity<GetAllEmployeeApiResponse> getAllEmployees() throws IOException;

    @GetMapping("/search/{searchString}")
    ResponseEntity<GetAllEmployeeApiResponse> getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping("/employees/{id}")
    ResponseEntity<GetEmployeeByIdResponse> getEmployeeById(@PathVariable String id);

    @GetMapping("/highestSalary")
    ResponseEntity<HighestSalaryOfAllEmployee> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<TopNEmployeeNames> getTopTenHighestEarningEmployeeNames();

    @PostMapping()
    ResponseEntity<CreateEmployeeResponse> createEmployee(@RequestBody Map<String, Object> employeeInput);

    @DeleteMapping("/{id}")
    ResponseEntity<DeleteEmployeeResponse> deleteEmployeeById(@PathVariable String id) throws EntityDoesNotExistException;

}
