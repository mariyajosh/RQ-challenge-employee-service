package com.example.rqchallenge.service;

import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
import com.example.rqchallenge.model.web.request.CreateEmployeeRequest;
import com.example.rqchallenge.model.web.response.DeleteEmployeeResponse;
import com.example.rqchallenge.model.web.response.EmployeeName;
import com.example.rqchallenge.model.web.response.TopNEmployeeNames;
import com.example.rqchallenge.service.client.EmployeeDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

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
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(List.of(new Employee("1", "Peter", 27, 1899877))));
        Employees expectedEmployees = new Employees(List.of(new Employee("1", "Peter", 27, 1899877)));

        Employees actualEmployees = employeeService.getEmployees();

        Assertions.assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void shouldReturnAllEmployeesIfSearchTokenMatchesName(){
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(
                List.of(new Employee("1", "Peter", 27, 1899877),
                        new Employee("2", "John", 27, 289987),
                        new Employee("3", "Mark", 27, 1899877)
                )));
        Employees expectedEmployees = new Employees(List.of(new Employee("2", "John", 27, 289987)));

        Employees actualEmployees = employeeService.getEmployeesByNameSearch("John");

        Assertions.assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void shouldReturnAllEmployeesIfSearchTokenMatchesNameByIgnoringCaseSensitive(){
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(
                List.of(new Employee("1", "Peter", 27, 1899877),
                        new Employee("2", "John", 27, 289987),
                        new Employee("3", "Mark", 27, 1899877)
                )));
        Employees expectedEmployees = new Employees(List.of(new Employee("2", "John", 27, 289987)));

        Employees actualEmployees = employeeService.getEmployeesByNameSearch("john");

        Assertions.assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void shouldReturnAllEmployeesIfSearchTokenContainsInNameByIgnoringCaseSensitive(){
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(
                List.of(new Employee("1", "Peter", 27, 1899877),
                        new Employee("2", "John", 27, 289987),
                        new Employee("3", "Josh", 27, 289987),
                        new Employee("4", "Mark", 27, 1899877)
                )));
        Employees expectedEmployees = new Employees(List.of(
                new Employee("2", "John", 27, 289987),
                new Employee("3", "Josh", 27, 289987)));

        Employees actualEmployees = employeeService.getEmployeesByNameSearch("jo");

        Assertions.assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void shouldReturnEmployeeIfThereExistEmployeeWithId(){
        Mockito.when(employeeDetailsService.getEmployeeById("1")).thenReturn(Optional.of(new Employee("2", "John", 27, 289987)));

        Optional<Employee> employeesById = employeeService.getEmployeesById("1");

        Mockito.verify(employeeDetailsService, Mockito.times(1)).getEmployeeById("1");
        Assertions.assertEquals(employeesById, Optional.of(new Employee("2", "John", 27, 289987)));
    }

    @Test
    void shouldReturnOptionalOfEmptyIfThereExistNoEmployeeWithId(){
        Mockito.when(employeeDetailsService.getEmployeeById("1")).thenReturn(Optional.empty());

        Optional<Employee> employeesById = employeeService.getEmployeesById("1");

        Mockito.verify(employeeDetailsService, Mockito.times(1)).getEmployeeById("1");
        Assertions.assertEquals(Optional.empty(), employeesById);
    }

    @Test
    void shouldGetHighestSalaryOfEmployees(){
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(
                List.of(new Employee("1", "Peter", 27, 189987),
                        new Employee("2", "John", 27, 289987),
                        new Employee("3", "Josh", 27, 239987),
                        new Employee("4", "Mark", 27, 1899877)
                )));

        int highestSalaryOfEmployees = employeeService.getHighestSalaryOfEmployees();

        Assertions.assertEquals(1899877, highestSalaryOfEmployees);
    }

    @Test
    void shouldGetSuccessStatusIfEmployeeCreatedSuccessFully(){
        Mockito.when(employeeDetailsService.createEmployee(any())).thenReturn("Success");
        CreateEmployeeRequest employee = new CreateEmployeeRequest("Peter", "1", "78437");

        String employeeCreationStatus = employeeService.createEmployee(employee);

        Assertions.assertEquals("Success", employeeCreationStatus);
    }

    @Test
    void shouldReturnTopNHighestSalariedEmployees(){
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(
                List.of(new Employee("1", "Peter", 27, 1002),
                        new Employee("2", "John", 27, 1001),
                        new Employee("3", "Josh", 27, 1004),
                        new Employee("4", "Mark", 27, 1003)
                )));
        List<EmployeeName> employeeNames = List.of(new EmployeeName("Josh"), new EmployeeName("Mark"));
        TopNEmployeeNames expectedResponse = new TopNEmployeeNames(employeeNames);

        TopNEmployeeNames topNHighestEarningEmployee = employeeService.getTopNHighestEarningEmployee(2);

        Assertions.assertEquals(expectedResponse, topNHighestEarningEmployee);

    }

    @Test
    void shouldDeleteEmployeeSuccessfully() {
        Mockito.when(employeeDetailsService.deleteEmployee("1")).thenReturn("Success");
        DeleteEmployeeResponse expectedResponse = new DeleteEmployeeResponse("Success");

        DeleteEmployeeResponse deleteEmployeeResponse = employeeService.deleteEmployee("1");

        Assertions.assertEquals(expectedResponse, deleteEmployeeResponse);
    }

}