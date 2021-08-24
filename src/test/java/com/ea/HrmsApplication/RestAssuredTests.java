package com.ea.HrmsApplication;


import com.ea.HrmsApplication.model.Address;
import com.ea.HrmsApplication.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestAssuredTests {

    @Value("${base.url}")
    private String baseUrl;

    @LocalServerPort
    private int port;

    @Test
    public void testGETEmployeeByID() {
        given()
                .baseUri(baseUrl)
                .port(port)
                .basePath("/employee/2")
                .get().then().assertThat().body("name", Matchers.equalTo("Prashanth"));
    }

    @Test
    public void testGetEmployees() {

        //Arrange
        var employee = Employee.builder()
                .id(1)
                .email("karthik@test.com")
                .name("Karthik")
                .phone(23423423)
                .address(Address.builder().city("Auckland").country("NZ").street("12 Street").build())
                .build();

        //Act
        var response = given().baseUri(baseUrl)
                .port(port)
                .basePath("/employee/1")
                .get();

        var responseEntity = response.body().as(Employee.class);


        //Assert
        assertThat(responseEntity).isEqualTo(employee);
    }

    @Test
    public void testPOSTEmployee() throws JsonProcessingException {

        //Arrange
        var employee = Employee.builder()
                .id(4)
                .email("John@test.com")
                .name("John")
                .phone(3454355)
                .address(Address.builder().city("Chennai").country("India").street("60 Street").build())
                .build();

        //Act
        var response =
                given()
                        .baseUri(baseUrl)
                        .port(port)
                        .basePath("/employee")
                        .contentType("application/json")
                        .body(employee)
                        .post();

        var responseEntity = response.body().as(Employee[].class);

        var responseEmployee = Arrays.stream(responseEntity).filter(x -> x.getId() == 4).findFirst().get();

        //assert if the response has the recently POSTed employee
        assertThat(responseEmployee).isEqualTo(employee);
    }

    @Test
    public void testPUTEmployee() throws JsonProcessingException {

        //Arrange
        var employee = Employee.builder()
                .id(3)
                .email("John@test.com")
                .name("John")
                .phone(3454355)
                .address(Address.builder().city("Chennai").country("India").street("60 Street").build())
                .build();

        //Act
        var response =
                given()
                        .baseUri(baseUrl)
                        .port(port)
                        .basePath("/employee/3")
                        .contentType("application/json")
                        .body(employee)
                        .put();

        var responseEntity = response.body().as(Employee.class);

        //assert if the response has the recently POSTed employee
        assertThat(responseEntity).isEqualTo(employee);
    }
}


