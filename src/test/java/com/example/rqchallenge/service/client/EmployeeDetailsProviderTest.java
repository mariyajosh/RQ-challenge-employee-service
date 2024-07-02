package com.example.rqchallenge.service.client;

import com.example.rqchallenge.config.properties.ExternalServiceResourceProperties;
import com.example.rqchallenge.exception.ExternalServiceException;
import com.example.rqchallenge.model.business.Employee;
import com.example.rqchallenge.model.business.Employees;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class EmployeeDetailsProviderTest {

    @MockBean
    private ExternalServiceResourceProperties externalServiceResourceProperties;
    @Autowired
    private EmployeeDetailsProvider employeeDetailsProvider;
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

        Employees allEmployees = employeeDetailsProvider.getAllEmployees();

        Assertions.assertEquals(allEmployees.getEmployeeList(), List.of(new Employee("1", "Tiger Nixon", 61, 320800)));
    }

    @Test
    void shouldThrowExternalServiceExceptionIfStatusCodeIs400FromExternalService(){
        int port = mockWebServer.getPort();
        Mockito.when(externalServiceResourceProperties.getBaseUrl()).thenReturn("http://localhost:"+port);
        Mockito.when(externalServiceResourceProperties.getEmployees()).thenReturn("/employees");
        mockWebServer.enqueue(new MockResponse().setResponseCode(400));

       Assertions.assertThrows(ExternalServiceException.class, () -> employeeDetailsProvider.getAllEmployees());
    }

    @Test
    void shouldReturnEmptyDtoIfBodyIfThereIsNoBodyFromExternalServiceWhileGettingAllEmployees(){
        int port = mockWebServer.getPort();
        Mockito.when(externalServiceResourceProperties.getBaseUrl()).thenReturn("http://localhost:"+port);
        Mockito.when(externalServiceResourceProperties.getEmployees()).thenReturn("/employees");
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        Employees actualResponse = employeeDetailsProvider.getAllEmployees();

        Assertions.assertEquals(new Employees(), actualResponse);
    }

    @Test
    void shouldReturnOptionalOfEmployeeWhileGettingEmployeeById(){
        int port = mockWebServer.getPort();
        Mockito.when(externalServiceResourceProperties.getBaseUrl()).thenReturn("http://localhost:"+port);
        Mockito.when(externalServiceResourceProperties.getEmployeeById()).thenReturn("/employees/1");
        mockWebServer.enqueue(new MockResponse()
                .setBody(sampleEmployeeByIdResponse())
                .addHeader("content-type", "application/json")
                .setResponseCode(200));

        Optional<Employee> employeeById = employeeDetailsProvider.getEmployeeById("1");

        Assertions.assertEquals(Optional.of(new Employee("1", "Foo Bar", 61, 320800)), employeeById);
    }

    @Test
    void shouldReturnOptionalOfEmptyIfThereIsNoBodyFromExternalServiceWhileGettingEmployeeById(){
        int port = mockWebServer.getPort();
        Mockito.when(externalServiceResourceProperties.getBaseUrl()).thenReturn("http://localhost:"+port);
        Mockito.when(externalServiceResourceProperties.getEmployeeById()).thenReturn("/employees/1");
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        Optional<Employee> employeeById = employeeDetailsProvider.getEmployeeById("1");

        Assertions.assertEquals(Optional.empty(), employeeById);
    }
    @Nested
    @TestPropertySource(
            properties = {
                 "api.external-service.maxRetryAttempts=0L"
            }
    )
    @DisplayName("Testing the retry mechanism")
    class NoRetryCheck{
        @Test
        void shouldRetryIfThereIs503StatusFromExternalServiceWhileFetingEmployees(){
            int port = mockWebServer.getPort();
            Mockito.when(externalServiceResourceProperties.getBaseUrl()).thenReturn("http://localhost:"+port);
            Mockito.when(externalServiceResourceProperties.getEmployees()).thenReturn("/employees");
            MockResponse mockResponse = new MockResponse()
                    .addHeader("content-type", "application/json")
                    .setBody(sampleJsonBody())
                    .setResponseCode(200);
            mockWebServer.enqueue(new MockResponse().setResponseCode(503));
            mockWebServer.enqueue(mockResponse);

            Employees allEmployees = employeeDetailsProvider.getAllEmployees();

            Assertions.assertEquals(allEmployees.getEmployeeList(), List.of(new Employee("1", "Tiger Nixon", 61, 320800)));

        }

        @Test
        void shouldRetryIfThereIs429ResponseCodeWhileGettingEmployeeById(){
            int port = mockWebServer.getPort();
            Mockito.when(externalServiceResourceProperties.getBaseUrl()).thenReturn("http://localhost:"+port);
            Mockito.when(externalServiceResourceProperties.getEmployeeById()).thenReturn("/employees/1");
            mockWebServer.enqueue(new MockResponse().setResponseCode(429));
            mockWebServer.enqueue(new MockResponse()
                    .setBody(sampleEmployeeByIdResponse())
                    .addHeader("content-type", "application/json")
                    .setResponseCode(200));

            Optional<Employee> employeeById = employeeDetailsProvider.getEmployeeById("1");

            Assertions.assertEquals(Optional.of(new Employee("1", "Foo Bar", 61, 320800)), employeeById);
        }
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

    private String sampleEmployeeByIdResponse(){
        return "{\n" +
                "        \"status\": \"success\",\n" +
                "        \"data\": {\n" +
                "            \"id\": \"1\",\n" +
                "            \"employee_name\": \"Foo Bar\",\n" +
                "            \"employee_salary\": \"320800\",\n" +
                "            \"employee_age\": \"61\"\n" +
                "        }\n" +
                "    }";
    }
}


