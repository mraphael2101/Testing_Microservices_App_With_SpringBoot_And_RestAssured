package com.ea.HrmsApplication;

import com.ea.HrmsApplication.model.Address;
import com.ea.HrmsApplication.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringTestRestTemplateTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	void testGetEmployeeByID() {

		//Arrange
		final String baseUrl = "http://localhost:" + port + "/employee/1";
		var employee = Employee.builder()
									.id(1)
									.email("karthik@test.com")
									.name("Karthik")
									.phone(23423423)
									.address(Address.builder().city("Auckland").country("NZ").street("12 Street").build())
									.build();


		//Act
		var responseEntity = this.restTemplate.getForObject(baseUrl, Employee.class);


		//Assert
		assertThat(responseEntity).isEqualTo(employee);
	}

	@Test
	public void testGetEmployees() {

		//Arrange
		final String baseUrl = "http://localhost:" + port + "/employee";
		var employee = Employee.builder()
				.id(1)
				.email("karthik@test.com")
				.name("Karthik")
				.phone(23423423)
				.address(Address.builder().city("Auckland").country("NZ").street("12 Street").build())
				.build();

		//Act
		var responseEntity = this.restTemplate.getForObject(baseUrl, Employee[].class);
		var responseEmployee = Arrays.stream(responseEntity).filter(x -> x.getId() == 1).findFirst().get();


		//Assert
		assertThat(responseEmployee).isEqualTo(employee);

	}

	@Test
	public void testPOSTEmployees() {

		//Arrange
		final String baseUrl = "http://localhost:" + port + "/employee";
		var employee = Employee.builder()
				.id(4)
				.email("john@test.com")
				.name("john")
				.phone(345434234)
				.address(Address.builder().city("Chennai").country("India").street("12 Street").build())
				.build();


		//act
		var responseEntity = this.restTemplate.postForObject(baseUrl, employee, Employee[].class);
		var responseEmployee = Arrays.stream(responseEntity).filter(x -> x.getId() == 4).findFirst().get();


		//assertion
		assertThat(responseEmployee).isEqualTo(employee);

	}

}
