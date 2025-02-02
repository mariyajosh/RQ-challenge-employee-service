package com.example.rqchallenge.service;

import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
import com.example.rqchallenge.model.web.request.CreateEmployeeRequest;
import com.example.rqchallenge.model.web.response.DeleteEmployeeResponse;
import com.example.rqchallenge.model.web.response.EmployeeName;
import com.example.rqchallenge.model.web.response.TopNEmployeeNames;
import com.example.rqchallenge.service.client.EmployeeDetailsProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EmployeeService {
    private EmployeeDetailsProvider employeeDetailsProvider;
    public Employees getEmployees() {
        return employeeDetailsProvider.getAllEmployees();
    }

    public Employees getEmployeesByNameSearch(String searchToken) {
        Employees employees = employeeDetailsProvider.getAllEmployees();
        List<Employee> employeeListByName = employees.getEmployeeList()
                .stream().
                filter(employee -> employee.getName().toLowerCase()
                        .contains(searchToken.toLowerCase()))
                .collect(Collectors.toList());
        return new Employees(employeeListByName);
    }

    public Optional<Employee> getEmployeesById(String id) {

        return employeeDetailsProvider.getEmployeeById(id);
    }

    public int getHighestSalaryOfEmployees() {
        Employees employees = employeeDetailsProvider.getAllEmployees();
            return employees.getEmployeeList().stream()
                .mapToInt(Employee::getSalary).max().getAsInt();
    }

    public String createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        return employeeDetailsProvider.createEmployee(createEmployeeRequest);
    }

    public TopNEmployeeNames getTopNHighestEarningEmployee(int N) {
        Employees employees = employeeDetailsProvider.getAllEmployees();
        List<EmployeeName> employeeNameList = employees.getEmployeeList().stream()
                .sorted(Comparator.comparing(Employee::getSalary, Comparator.reverseOrder()))
                .limit(N)
                .map(employee -> new EmployeeName(employee.getName()))
                .collect(Collectors.toList());
        return new TopNEmployeeNames(employeeNameList);
    }

    public DeleteEmployeeResponse deleteEmployee(String id) {
        String deletionOperationStatus = employeeDetailsProvider.deleteEmployee(id);
        return new DeleteEmployeeResponse(deletionOperationStatus);
    }
}
