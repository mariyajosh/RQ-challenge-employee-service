package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.Employees;
import com.example.rqchallenge.model.response.GetAllApiResponse;
import com.example.rqchallenge.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee-service")
@AllArgsConstructor
public class EmployeeController implements IEmployeeController{

    private final EmployeeService employeeService;
    @Override
    public ResponseEntity<GetAllApiResponse> getAllEmployees() {
        Employees employees = employeeService.getEmployees();
        GetAllApiResponse response = employees.toGetAllAPIResponse();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return null;
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return null;
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return null;
    }
}
