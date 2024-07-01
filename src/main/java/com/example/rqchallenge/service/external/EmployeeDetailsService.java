package com.example.rqchallenge.service.external;


import com.example.rqchallenge.config.properties.ExternalServiceResourceProperties;
import com.example.rqchallenge.exception.ExternalServiceException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.Employees;
import com.example.rqchallenge.model.external.EmployeeByIdDTO;
import com.example.rqchallenge.model.external.EmployeeCreationDetailsDTO;
import com.example.rqchallenge.model.external.EmployeesDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class EmployeeDetailsService {
    private final ExternalServiceResourceProperties externalServiceResourceProperties;
    private final RestTemplate restTemplate;


    public Employees getAllEmployees() {
        log.info("Calling external service to get all employees");
        String resourceUrl = externalServiceResourceProperties.getBaseUrl() + externalServiceResourceProperties.getEmployees();
        try {
            ResponseEntity<EmployeesDTO> responseEntity = restTemplate.getForEntity(resourceUrl, EmployeesDTO.class);
            EmployeesDTO employeesDTO = responseEntity.getBody() != null ? responseEntity.getBody() : new EmployeesDTO();
            return employeesDTO.toEmployees();
        } catch (Exception exception) {
            log.error("Error occurred interacting with resource {}", resourceUrl, exception);
            throw new ExternalServiceException(exception, "Exception occurred while fetching employees");
        }
    }

    public Employee getEmployeeById(String id) {
        log.info("Calling external Service to get employee by id");
        String resourceUrl = externalServiceResourceProperties.getBaseUrl() + externalServiceResourceProperties.getEmployeeById();
        try {
            ResponseEntity<EmployeeByIdDTO> responseEntity = restTemplate.getForEntity(resourceUrl, EmployeeByIdDTO.class, id);
            EmployeeByIdDTO employeeByIdDTO = responseEntity.getBody() != null ? responseEntity.getBody() : new EmployeeByIdDTO();
            return employeeByIdDTO.toEmployee();
        } catch (Exception exception) {
            log.error("Error occurred interacting with resource {}", resourceUrl, exception);
            throw new ExternalServiceException(exception, "Exception occurred while fetching employees");
        }
    }

    public String createEmployee(Map<String, Object> employeeInput) {
        log.info("Calling external service to create employee");
        String resourceUrl = externalServiceResourceProperties.getBaseUrl() + externalServiceResourceProperties.getCreateEmployee();
        try{
            ResponseEntity<EmployeeCreationDetailsDTO> employeeCreationDetailsResponseEntity = restTemplate.postForEntity(resourceUrl, employeeInput, EmployeeCreationDetailsDTO.class);
            EmployeeCreationDetailsDTO employeeCreationDetailsDTO = employeeCreationDetailsResponseEntity.getBody();
           return employeeCreationDetailsDTO.getStatus();
        }
        catch (Exception exception) {
            log.error("Error occurred interacting with resource {}", resourceUrl, exception);
            throw new ExternalServiceException(exception, "Exception occurred creating employee");
        }
    }

    public void deleteEmployee(String id) {
        log.info("Calling external service to create employee");
        String resourceUrl = externalServiceResourceProperties.getBaseUrl() + externalServiceResourceProperties.getDeleteEmployee();
        try{
            restTemplate.delete(resourceUrl, id);
        }
        catch (Exception exception) {
            log.error("Error occurred interacting with resource {}", resourceUrl, exception);
            throw new ExternalServiceException(exception, "Exception occurred deleting employee with id: " + id);
        }
    }
}
