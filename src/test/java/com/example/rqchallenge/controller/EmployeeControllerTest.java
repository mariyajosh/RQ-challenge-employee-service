package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
import com.example.rqchallenge.model.web.request.CreateEmployeeRequest;
import com.example.rqchallenge.service.client.EmployeeDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeDetailsService employeeDetailsService;
    private static final String EMPLOYEE_SERVICE_BASE_URL = "/employee-service";
    private static final String GET_ALL_EMPLOYEES_URI = "/employees";
    private static final String SEARCH_EMPLOYEE_BY_NAME = "/employees/search/{searchString}";
    private static final String SEARCH_EMPLOYEE_BY_ID = "/employees/{id}";
    private static final String HIGHEST_SALARY_OF_EMPLOYEES = "/employees/highestSalary";
    private static final String TOP_TEN_EMPLOYEES_WITH_HIGHEST_SALARY = "/employees/topTenHighestEarningEmployeeNames";
    private static final String CREATE_EMPLOYEE = "/employee";
    private static final String DELETE_EMPLOYEE = "/employees/{id}";


    @Test
    void shouldGet2xxResponseWhileGettingAllEmployees() throws Exception {
        List<Employee> employees = List.of(new Employee("1", "Peter", 27, 8937313),
                new Employee("2", "Peter", 26, 7937313));
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(employees));
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_SERVICE_BASE_URL + GET_ALL_EMPLOYEES_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].name", is("Peter")));
    }

    @Test
    void shouldReturn2xxWhileSearchingEmployeesByName() throws Exception {
        List<Employee> employees = List.of(new Employee("1", "Peter", 27, 8937313),
                new Employee("2", "peter asc", 27, 837313),
                new Employee("3", "John", 26, 7937313));
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(employees));
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_SERVICE_BASE_URL + SEARCH_EMPLOYEE_BY_NAME, "peter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].name", is("Peter")))
                .andExpect(jsonPath("$.employees.size()", is(2)));

    }

    @Test
    void shouldReturn2xxResponseWithEmployeeWhileFindingEmployeeById() throws Exception {
        Employee employee = new Employee("1", "Peter", 27, 8937313);
        Mockito.when(employeeDetailsService.getEmployeeById("1")).thenReturn(Optional.of(employee));
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_SERVICE_BASE_URL + SEARCH_EMPLOYEE_BY_ID, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeDetails.name", is("Peter")));
    }

    @Test
    void shouldReturn2xxResponseWithHighestSalaryOfEmployee() throws Exception {
        List<Employee> employees = List.of(new Employee("1", "Peter", 27, 1000),
                new Employee("2", "John", 26, 1001),
                new Employee("3", "Jack", 26, 1002),
                new Employee("4", "Marco", 26, 1004));
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(employees));
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_SERVICE_BASE_URL + HIGHEST_SALARY_OF_EMPLOYEES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salary", is(1004)));
    }

    @Test
    void shouldReturn2xxResponseWithTopTenEmployeesWithHighestSalary() throws Exception {
        List<Employee> employees = List.of(new Employee("1", "Peter", 27, 1000),
                new Employee("2", "John", 26, 1001),
                new Employee("3", "Jack", 26, 1002),
                new Employee("4", "Marco", 26, 1004));
        Mockito.when(employeeDetailsService.getAllEmployees()).thenReturn(new Employees(employees));
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_SERVICE_BASE_URL + TOP_TEN_EMPLOYEES_WITH_HIGHEST_SALARY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeNameList[1].name", is("Jack")))
                .andExpect(jsonPath("$.employeeNameList.size()", is(4)));
    }

    @Test
    void shouldReturn201ResponseWithEmployeeCreationStatus() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateEmployeeRequest employee = new CreateEmployeeRequest("Peter", "1", "78437");
        Mockito.when(employeeDetailsService.createEmployee(employee)).thenReturn("Success");
        mockMvc.perform(MockMvcRequestBuilders.post(EMPLOYEE_SERVICE_BASE_URL + CREATE_EMPLOYEE)
                        .content(objectMapper.writeValueAsString(employee))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("Success")));
    }

    @Test
    void shouldThrow400BadRequestIfAgeIsInvalid() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateEmployeeRequest employee = new CreateEmployeeRequest("", "-1", "78437");
        Mockito.when(employeeDetailsService.createEmployee(employee)).thenReturn("Success");
        mockMvc.perform(MockMvcRequestBuilders.post(EMPLOYEE_SERVICE_BASE_URL + CREATE_EMPLOYEE)
                        .content(objectMapper.writeValueAsString(employee))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorDetails.size()", is(2)));
    }

    @Test
    void shouldReturn2xxStatusCodeWithDeletedEmployeeName() throws Exception {
        Mockito.when(employeeDetailsService.deleteEmployee("1")).thenReturn("Success");
        mockMvc.perform(MockMvcRequestBuilders.delete(EMPLOYEE_SERVICE_BASE_URL + DELETE_EMPLOYEE, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Success")));
    }
}