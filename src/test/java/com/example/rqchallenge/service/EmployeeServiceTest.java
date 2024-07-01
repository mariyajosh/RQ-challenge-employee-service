package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.Employees;
import com.example.rqchallenge.service.external.EmployeeDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class EmployeeServiceTest {
    private final EmployeeDetailsService employeeDetailsService = Mockito.mock(EmployeeDetailsService.class);
    private final EmployeeService employeeService = new EmployeeService(employeeDetailsService);

    @Test
    void shouldInvokeEmployeeDetailsService(){
        employeeService.getEmployees();
        Mockito.verify(employeeDetailsService, Mockito.timeout(1)).getAllEmployees();
    }

    @Test
    void shouldReturnExpectedResponseAfterInvokingEmployeeDetailsService(){
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(List.of(new Employee(1, "Peter", 27, 1899877))));
        Employees expectedEmployees = new Employees(List.of(new Employee(1, "Peter", 27, 1899877)));

        Employees actualEmployees = employeeService.getEmployees();

        Assertions.assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void shouldReturnAllEmployeesIfSearchTokenMatchesName(){
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(
                List.of(new Employee(1, "Peter", 27, 1899877),
                        new Employee(2, "John", 27, 289987),
                        new Employee(3, "Mark", 27, 1899877)
                )));
        Employees expectedEmployees = new Employees(List.of(new Employee(2, "John", 27, 289987)));

        Employees actualEmployees = employeeService.getEmployeesByNameSearch("John");

        Assertions.assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void shouldReturnAllEmployeesIfSearchTokenMatchesNameByIgnoringCaseSensitive(){
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(
                List.of(new Employee(1, "Peter", 27, 1899877),
                        new Employee(2, "John", 27, 289987),
                        new Employee(3, "Mark", 27, 1899877)
                )));
        Employees expectedEmployees = new Employees(List.of(new Employee(2, "John", 27, 289987)));

        Employees actualEmployees = employeeService.getEmployeesByNameSearch("john");

        Assertions.assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void shouldReturnAllEmployeesIfSearchTokenContainsInNameByIgnoringCaseSensitive(){
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(
                List.of(new Employee(1, "Peter", 27, 1899877),
                        new Employee(2, "John", 27, 289987),
                        new Employee(3, "Josh", 27, 289987),
                        new Employee(4, "Mark", 27, 1899877)
                )));
        Employees expectedEmployees = new Employees(List.of(
                new Employee(2, "John", 27, 289987),
                new Employee(3, "Josh", 27, 289987)));

        Employees actualEmployees = employeeService.getEmployeesByNameSearch("jo");

        Assertions.assertEquals(expectedEmployees, actualEmployees);
    }

}