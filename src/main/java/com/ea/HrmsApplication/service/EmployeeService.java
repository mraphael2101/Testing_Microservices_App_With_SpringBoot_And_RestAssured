package com.ea.HrmsApplication.service;

import com.ea.HrmsApplication.model.Address;
import com.ea.HrmsApplication.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    //Entity
    private List<Employee> employeeLists = new ArrayList<>(List.of(
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

    public List<Employee> getAllEmployees() {
        return employeeLists;
    }

    public Employee getEmployeeById(int id) {
        return employeeLists.stream()
                .filter(x -> x.getId() == id)
                .findFirst().get();
    }

    public List<Employee> addEmployee(Employee employee) {
        employeeLists.add(employee);
        return employeeLists;
    }


    public Employee updateEmployee(int id, Employee employee) {
        return employeeLists.stream()
                .filter(x -> x.getId() == id)
                .peek(o -> o.setName(employee.getName()))
                .peek(o -> o.setEmail(employee.getEmail()))
                .peek(o -> o.setPhone(employee.getPhone()))
                .peek(o -> o.setAddress(employee.getAddress()))
                .findFirst().get();
    }

    public List<Employee> deleteEmployee(int id) {
        employeeLists.removeIf(x -> x.getId() == id);
        return employeeLists;
    }
}
