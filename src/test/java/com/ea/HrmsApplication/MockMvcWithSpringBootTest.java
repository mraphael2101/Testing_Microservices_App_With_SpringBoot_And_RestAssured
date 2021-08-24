package com.ea.HrmsApplication;


import com.ea.HrmsApplication.controller.EmployeeController;
import com.ea.HrmsApplication.model.Address;
import com.ea.HrmsApplication.model.Employee;
import com.ea.HrmsApplication.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class MockMvcWithSpringBootTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testGetEmployeeByID() throws Exception {

        //arrange
        var employee = Employee.builder()
                .id(1)
                .email("karthik@test.com")
                .name("Karthik")
                .phone(23423423)
                .address(Address.builder().city("Auckland").country("NZ").street("12 Street").build())
                .build();

        //act
        when(employeeService.getEmployeeById(1)).thenReturn(employee);


        //assertion
        this.mockMvc.perform(get("/employee/1")).andExpect(status().isOk());
    }


    @Test
    public void testGetEmployee() throws Exception {

        //arrange
        var employee = Employee.builder()
                .id(1)
                .email("karthik@test.com")
                .name("Karthik")
                .phone(23423423)
                .address(Address.builder().city("Auckland").country("NZ").street("12 Street").build())
                .build();

        //act
        when(employeeService.getEmployeeById(1)).thenReturn(employee);

        //assert
        var result = this.mockMvc.perform(MockMvcRequestBuilders.get("/employee/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Karthik"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetEmployeeList() throws Exception {

        //arrange
        //Entity
        List<Employee> employeeLists = new ArrayList<>(List.of(
                Employee.builder()
                        .id(1)
                        .email("karthik@test.com")
                        .name("Karthik")
                        .phone(23423423)
                        .address(Address.builder().city("Auckland").country("NZ").street("12 Street").build())
                        .build(),
                Employee.builder().email("prashanth@test.com")
                        .id(2)
                        .name("Prashanth")
                        .phone(3454343)
                        .address(Address.builder().city("Auckland").country("NZ").street("12 Street").build())
                        .build(),
                Employee.builder()
                        .id(3)
                        .email("Carlos@test.com")
                        .name("Carlos")
                        .phone(5675675)
                        .address(Address.builder().city("Sydney").country("Aus").street("45 Grey Street").build())
                        .build()
        ));

        //act
        when(employeeService.getAllEmployees()).thenReturn(employeeLists);

        //assert
        var result = this.mockMvc.perform(MockMvcRequestBuilders.get("/employee"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Karthik"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address.city").value("Auckland"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}
