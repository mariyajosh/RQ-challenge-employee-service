package com.example.rqchallenge.service.client;

import com.example.rqchallenge.config.properties.ExternalServiceResourceProperties;
import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class EmployeeDetailsServiceTest {

    private final ExternalServiceResourceProperties externalServiceResourceProperties = Mockito.mock(ExternalServiceResourceProperties.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final EmployeeDetailsService employeeDetailsService = new EmployeeDetailsService(externalServiceResourceProperties, restTemplate);
    private static MockWebServer mockWebServer;

    @BeforeAll
    static void init() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldConsumeResponseSuccessfullyFromExternalService(){
        int port = mockWebServer.getPort();
        Mockito.when(externalServiceResourceProperties.getBaseUrl()).thenReturn("http://localhost:"+port);
        Mockito.when(externalServiceResourceProperties.getEmployees()).thenReturn("/employees");
        MockResponse mockResponse = new MockResponse()
                .addHeader("content-type", "application/json")
                .setBody(sampleJsonBody())
                .setResponseCode(200);
        mockWebServer.enqueue(mockResponse);

        Employees allEmployees = employeeDetailsService.getAllEmployees();

        Assertions.assertEquals(allEmployees.getEmployeeList(), List.of(new Employee(1, "Tiger Nixon", 61, 320800)));

    }

    private String sampleJsonBody(){
        return "{\n" +
                "        \"status\": \"success\",\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "            \"id\": \"1\",\n" +
                "            \"employee_name\": \"Tiger Nixon\",\n" +
                "            \"employee_salary\": \"320800\",\n" +
                "            \"employee_age\": \"61\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }";
    }
}


