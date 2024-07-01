package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
import com.example.rqchallenge.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    private static final String EMPLOYEE_SERVICE_BASE_URL = "/employee-service";
    private static final String GET_ALL_EMPLOYEES_URI = "/employees";

    @Test
    void shouldGet2xxResponseWhileGettingAllEmployees() throws Exception {
        List<Employee> employees = List.of(new Employee(1, "Peter", 27, 8937313),
                new Employee(2, "Peter", 26, 7937313));
        Mockito.when(employeeService.getEmployees()).thenReturn(new Employees(employees));
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_SERVICE_BASE_URL + GET_ALL_EMPLOYEES_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].name", is("Peter")));
    }

    @Test
    void shouldReturn2xxWhileSearchingEmployeesByName() throws Exception {
        List<Employee> employees = List.of(new Employee(1, "Peter", 27, 8937313),
                new Employee(2, "Peter", 26, 7937313));
        Mockito.when(employeeService.getEmployees()).thenReturn(new Employees(employees));
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_SERVICE_BASE_URL + GET_ALL_EMPLOYEES_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].name", is("Peter")));
    }
}