package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.response.GetAllApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface IEmployeeController {

    @GetMapping("/employees")
    ResponseEntity<GetAllApiResponse> getAllEmployees() throws IOException;

    @GetMapping("/search/{searchString}")
    ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable String id);

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    @PostMapping()
    ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable String id);

}
