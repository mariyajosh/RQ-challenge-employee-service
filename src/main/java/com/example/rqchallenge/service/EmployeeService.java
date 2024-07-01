package com.example.rqchallenge.service;

import com.example.rqchallenge.exception.EntityDoesNotExistException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.Employees;
import com.example.rqchallenge.model.response.DeleteEmployeeResponse;
import com.example.rqchallenge.model.response.EmployeeName;
import com.example.rqchallenge.model.response.TopNEmployeeNames;
import com.example.rqchallenge.service.external.EmployeeDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    public Employee getEmployeesById(String id) {
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

    public TopNEmployeeNames getTopNHighestEarningEmployee() {
        Employees employees = employeeDetailsService.getAllEmployees();
        List<EmployeeName> employeeNameList = employees.getEmployeeList().stream()
                .sorted(Comparator.comparing(Employee::getSalary, Comparator.reverseOrder()))
                .limit(10)
                .map(employee -> new EmployeeName(employee.getName()))
                .collect(Collectors.toList());
        return new TopNEmployeeNames(employeeNameList);
    }

    public DeleteEmployeeResponse deleteEmployee(String id) throws EntityDoesNotExistException {
        Employee employeesById = getEmployeesById(id);
        if(employeesById == null){
            throw new EntityDoesNotExistException(String.format("Employee with id %s does not exist", id));
        }
        employeeDetailsService.deleteEmployee(id);
        return new DeleteEmployeeResponse(employeesById.getName());
    }
}
