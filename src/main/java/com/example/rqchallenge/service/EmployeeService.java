package com.example.rqchallenge.service;

import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
import com.example.rqchallenge.model.web.response.DeleteEmployeeResponse;
import com.example.rqchallenge.model.web.response.EmployeeName;
import com.example.rqchallenge.model.web.response.TopNEmployeeNames;
import com.example.rqchallenge.service.client.EmployeeDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EmployeeService {
    private EmployeeDetailsService employeeDetailsService;
    public Employees getEmployees() {
        return employeeDetailsService.getAllEmployees();
    }

    public Employees getEmployeesByNameSearch(String searchToken) {
        Employees employees = employeeDetailsService.getAllEmployees();
        List<Employee> employeeListByName = employees.getEmployeeList()
                .stream().
                filter(employee -> employee.getName().toLowerCase()
                        .contains(searchToken.toLowerCase()))
                .collect(Collectors.toList());
        return new Employees(employeeListByName);
    }

    public Optional<Employee> getEmployeesById(String id) {

        return employeeDetailsService.getEmployeeById(id);
    }

    public int getHighestSalaryOfEmployees() {
        Employees employees = employeeDetailsService.getAllEmployees();
            return employees.getEmployeeList().stream()
                .mapToInt(Employee::getSalary).max().getAsInt();
    }

    public String createEmployee(Map<String, Object> employeeInput) {
        return employeeDetailsService.createEmployee(employeeInput);
    }

    public TopNEmployeeNames getTopNHighestEarningEmployee(int N) {
        Employees employees = employeeDetailsService.getAllEmployees();
        List<EmployeeName> employeeNameList = employees.getEmployeeList().stream()
                .sorted(Comparator.comparing(Employee::getSalary, Comparator.reverseOrder()))
                .limit(N)
                .map(employee -> new EmployeeName(employee.getName()))
                .collect(Collectors.toList());
        return new TopNEmployeeNames(employeeNameList);
    }

    public DeleteEmployeeResponse deleteEmployee(String id) {
        String deletionOperationStatus = employeeDetailsService.deleteEmployee(id);
        return new DeleteEmployeeResponse(deletionOperationStatus);
    }
}
