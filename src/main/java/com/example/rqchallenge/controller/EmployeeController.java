package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
import com.example.rqchallenge.model.web.response.*;
import com.example.rqchallenge.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employee-service")
@AllArgsConstructor
public class EmployeeController implements IEmployeeController{

    private final EmployeeService employeeService;
    @Override
    public ResponseEntity<GetAllEmployeeApiResponse> getAllEmployees() {
        Employees employees = employeeService.getEmployees();
        GetAllEmployeeApiResponse response = employees.toGetAllAPIResponse();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GetAllEmployeeApiResponse> getEmployeesByNameSearch(String searchString) {
        Employees employees = employeeService.getEmployeesByNameSearch(searchString);
        GetAllEmployeeApiResponse response = employees.toGetAllAPIResponse();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GetEmployeeByIdResponse> getEmployeeById(String id) {
        Optional<Employee> employeesById = employeeService.getEmployeesById(id);
        return ResponseEntity.ok(new GetEmployeeByIdResponse(employeesById.map(Employee::toEmployeeDetails).orElse(null)));
    }

    @Override
    public ResponseEntity<HighestSalaryOfAllEmployee> getHighestSalaryOfEmployees() {
        int highestSalaryOfAllEmployees = employeeService.getHighestSalaryOfEmployees();
        return ResponseEntity.ok(new HighestSalaryOfAllEmployee(highestSalaryOfAllEmployees));
    }

    @Override
    public ResponseEntity<TopNEmployeeNames> getTopTenHighestEarningEmployeeNames(){
        return ResponseEntity.ok(employeeService.getTopNHighestEarningEmployee(10));
    }

    @Override
    public ResponseEntity<CreateEmployeeResponse> createEmployee(Map<String, Object> employeeInput) {
        String creationStatus = employeeService.createEmployee(employeeInput);
        return new ResponseEntity<>(new CreateEmployeeResponse(creationStatus), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DeleteEmployeeResponse> deleteEmployeeById(String id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
