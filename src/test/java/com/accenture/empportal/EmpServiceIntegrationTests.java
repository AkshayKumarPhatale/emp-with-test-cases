package com.accenture.empportal;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.accenture.empportal.entity.Employee;
import com.accenture.empportal.entity.Gender;
import com.accenture.empportal.entity.Role;
import com.accenture.empportal.request.EmployeeRequest;
import com.accenture.empportal.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmpServiceIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Test
	public void findByEmpIdTest() throws Exception {
		Employee employee = new Employee();
		employee.setCreatedAt(new Date());
		employee.setEmpGender(Gender.MALE);
		employee.setEmpId(1l);
		employee.setEmpName("akshay");
		employee.setEmpRole(Role.ASSOCIATE);
		employee.setEmpSalary(10000.00);
		employee.setUpdatedAt(new Date());

		Optional<Employee> optionalEmp = Optional.of(employee);

		Mockito.when(employeeService.findByEmpId(1l)).thenReturn(optionalEmp);

		mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}", 1l)).andExpect(status().isOk())
				.andExpect(jsonPath("$.empId").value(1));
		verify(employeeService).findByEmpId(1l);

	}
	
	

	@Test
	public void addEmployeeTest() throws Exception {
		EmployeeRequest empRequest = new EmployeeRequest();
		empRequest.setCreatedAt(new Date());
		empRequest.setEmpGender("MALE");
		empRequest.setEmpRole("ASSOCIATE");
		empRequest.setEmpSalary(1000.0);
		empRequest.setUpdatedAt(new Date());
		empRequest.setEmpName("Akshay");
		empRequest.setEmpId(1l);

		Employee employee = new Employee();

		Gender gender = Gender.valueOf(empRequest.getEmpGender());
		employee.setEmpGender(gender);
		employee.setEmpId(empRequest.getEmpId());
		employee.setEmpName(empRequest.getEmpName());
		employee.setEmpSalary(empRequest.getEmpSalary());
		employee.setCreatedAt(empRequest.getCreatedAt());
		employee.setUpdatedAt(empRequest.getUpdatedAt());
		Role role = Role.valueOf(empRequest.getEmpRole());
		employee.setEmpRole(role);

		ObjectMapper objectMapper = new ObjectMapper();
		String employeeAsJsonString = objectMapper.writeValueAsString(empRequest);

		Mockito.when(employeeService.addEmployee(empRequest)).thenReturn(Optional.ofNullable(employee));

		mockMvc.perform(MockMvcRequestBuilders.post("/employee/addEmployee/")
				.content(employeeAsJsonString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.employeeRequest.empId").value(1l))
				.andExpect(jsonPath("$.employeeRequest.empName").value("Akshay"))
				.andExpect(jsonPath("$.employeeRequest.empGender").value("MALE")).andDo(print());

		verify(employeeService).addEmployee(empRequest);

	}
	
	@Test
	void updateEmployeeTest() throws Exception {
		
		EmployeeRequest empRequest = new EmployeeRequest();
		empRequest.setCreatedAt(new Date());
		empRequest.setEmpGender("MALE");
		empRequest.setEmpRole("ASSOCIATE");
		empRequest.setEmpSalary(1000.0);
		empRequest.setUpdatedAt(new Date());
		empRequest.setEmpName("Akshay");
		empRequest.setEmpId(1l);
		
		Employee employee = new Employee();

		Gender gender = Gender.valueOf(empRequest.getEmpGender());
		employee.setEmpGender(gender);
		employee.setEmpId(empRequest.getEmpId());
		employee.setEmpName(empRequest.getEmpName());
		employee.setEmpSalary(empRequest.getEmpSalary());
		employee.setCreatedAt(empRequest.getCreatedAt());
		employee.setUpdatedAt(empRequest.getUpdatedAt());
		Role role = Role.valueOf(empRequest.getEmpRole());
		employee.setEmpRole(role);

		ObjectMapper objectMapper = new ObjectMapper();
		String employeeAsJsonString = objectMapper.writeValueAsString(empRequest);
		Mockito.when(employeeService.updateEmployee(empRequest)).thenReturn(Optional.ofNullable(employee));
		mockMvc.perform(MockMvcRequestBuilders.put("/employee/updateEmployee/").content(employeeAsJsonString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.employeeRequest.empId").value(1))
				.andExpect(jsonPath("$.employeeRequest.empName").value("Akshay"))
				.andExpect(jsonPath("$.employeeRequest.empGender").value("MALE")).andDo(print());

		verify(employeeService).updateEmployee((empRequest));
		
	}

}
