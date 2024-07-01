package com.example.rqchallenge.service.client;


import com.example.rqchallenge.config.properties.ExternalServiceResourceProperties;
import com.example.rqchallenge.exception.ErrorMessage;
import com.example.rqchallenge.exception.ExternalServiceException;
import com.example.rqchallenge.exception.ExternalServiceUnavailableException;
import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
import com.example.rqchallenge.model.client.EmployeeByIdDTO;
import com.example.rqchallenge.model.client.EmployeeCreationDetailsDTO;
import com.example.rqchallenge.model.client.EmployeesDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class EmployeeDetailsService {
    private final ExternalServiceResourceProperties externalServiceResourceProperties;
    private final RestTemplate restTemplate;


    @Retryable(value = ExternalServiceUnavailableException.class, backoff = @Backoff(delay = 1000))
    public Employees getAllEmployees() {
        String resourceUrl = externalServiceResourceProperties.getBaseUrl() + externalServiceResourceProperties.getEmployees();
        try {
            ResponseEntity<EmployeesDTO> responseEntity = restTemplate.getForEntity(resourceUrl, EmployeesDTO.class);
            EmployeesDTO employeesDTO = responseEntity.getBody() != null ? responseEntity.getBody() : new EmployeesDTO();
            return employeesDTO.toEmployees();
        } catch (Exception exception) {
            log.error(ErrorMessage.EXCEPTION_OCCURRED_WHILE_INTERACTING_WITH_RESOURCE, resourceUrl, exception);
            throw convertToAppropriateException(exception, ErrorMessage.EXCEPTION_WHILE_FETCHING_EMPLOYEES);
        }
    }

    @Retryable(value = ExternalServiceUnavailableException.class, backoff = @Backoff(delay = 1000))
    public Employee getEmployeeById(String id) {
        String resourceUrl = externalServiceResourceProperties.getBaseUrl() + externalServiceResourceProperties.getEmployeeById();
        try {
            ResponseEntity<EmployeeByIdDTO> responseEntity = restTemplate.getForEntity(resourceUrl, EmployeeByIdDTO.class, id);
            EmployeeByIdDTO employeeByIdDTO = responseEntity.getBody() != null ? responseEntity.getBody() : new EmployeeByIdDTO();
            return employeeByIdDTO.toEmployee();
        } catch (Exception exception) {
            log.error(ErrorMessage.EXCEPTION_OCCURRED_WHILE_INTERACTING_WITH_RESOURCE, resourceUrl, exception);
            throw convertToAppropriateException(exception, String.format(ErrorMessage.EXCEPTION_OCCURRED_WHILE_FINDING_EMPLOYEE, id));
        }
    }

    @Retryable(value = ExternalServiceUnavailableException.class, backoff = @Backoff(delay = 1000))
    public String createEmployee(Map<String, Object> employeeInput) {
        String resourceUrl = externalServiceResourceProperties.getBaseUrl() + externalServiceResourceProperties.getCreateEmployee();
        try{
            ResponseEntity<EmployeeCreationDetailsDTO> employeeCreationDetailsResponseEntity = restTemplate.postForEntity(resourceUrl, employeeInput, EmployeeCreationDetailsDTO.class);
            EmployeeCreationDetailsDTO employeeCreationDetailsDTO = employeeCreationDetailsResponseEntity.getBody();
           return employeeCreationDetailsDTO.getStatus();
        }
        catch (Exception exception) {
            log.error(ErrorMessage.EXCEPTION_OCCURRED_WHILE_INTERACTING_WITH_RESOURCE, resourceUrl, exception);
            throw new ExternalServiceException(ErrorMessage.EXCEPTION_WHILE_CREATING_EMPLOYEE);
        }
    }

    @Retryable(value = ExternalServiceUnavailableException.class, backoff = @Backoff(delay = 1000))
    public void deleteEmployee(String id) {
        String resourceUrl = externalServiceResourceProperties.getBaseUrl() + externalServiceResourceProperties.getDeleteEmployee();
        try{
            restTemplate.delete(resourceUrl, id);
        }
        catch (Exception exception) {
            log.error(ErrorMessage.EXCEPTION_OCCURRED_WHILE_INTERACTING_WITH_RESOURCE, resourceUrl, exception);
            throw new ExternalServiceException(String.format(ErrorMessage.EXCEPTION_OCCURRED_WHILE_DELETING_EMPLOYEE, id));
        }
    }

    private RuntimeException convertToAppropriateException(Exception exception, String message){
        if(exception instanceof HttpClientErrorException || exception instanceof HttpServerErrorException){
            HttpStatus statusCode = ((HttpStatusCodeException) exception).getStatusCode();
            if(statusCode == HttpStatus.TOO_MANY_REQUESTS || statusCode == HttpStatus.SERVICE_UNAVAILABLE){
                return new ExternalServiceUnavailableException();
            }
        }
        return new ExternalServiceException(message);
    }
}
