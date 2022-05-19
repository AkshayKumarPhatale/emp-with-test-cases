package com.accenture.empportal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
//import org.junit.Test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.accenture.empportal.entity.Employee;
import com.accenture.empportal.entity.Gender;
import com.accenture.empportal.entity.Role;
import com.accenture.empportal.exception.EmployeeCreationExpection;
import com.accenture.empportal.exception.EmployeeNotFoundException;
import com.accenture.empportal.exception.EmployeeUpdationFailed;
import com.accenture.empportal.exception.NoEmployeeFoundException;
import com.accenture.empportal.repository.EmployeeRepository;
import com.accenture.empportal.request.EmployeeRequest;
import com.accenture.empportal.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest

public class EmpServiceApplicationTests {
	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepository employeeRepository;

	@Test
	public void addEmployeetest() {
		EmployeeRequest empRequest = new EmployeeRequest();
		empRequest.setCreatedAt(new Date());
		empRequest.setEmpGender("MALE");
		empRequest.setEmpRole("ASSOCIATE");
		empRequest.setEmpSalary(1000.0);
		empRequest.setUpdatedAt(new Date());
		empRequest.setEmpName("Akshay");
		

		Employee employee = new Employee();
		

		Gender gender = Gender.valueOf(empRequest.getEmpGender());
		employee.setEmpGender(gender);
		employee.setEmpName(empRequest.getEmpName());
		employee.setEmpSalary(empRequest.getEmpSalary());
		employee.setCreatedAt(empRequest.getCreatedAt());
		employee.setUpdatedAt(empRequest.getUpdatedAt());
		Role role = Role.valueOf(empRequest.getEmpRole());
		employee.setEmpRole(role);
		
        
		Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
		Optional<Employee> savedEmployee = employeeService.addEmployee(empRequest);
		Assertions.assertThat(savedEmployee.get()).isEqualTo(employee);
		verify(employeeRepository).save(any(Employee.class));

	}

	
	@Test
	public void addEmployeetestFail() {
		EmployeeRequest empRequest = new EmployeeRequest();
		empRequest.setCreatedAt(new Date());
		empRequest.setEmpGender("MALE");
		empRequest.setEmpRole("ASSOCIATE");
		empRequest.setEmpSalary(1000.0);
		empRequest.setUpdatedAt(new Date());
		empRequest.setEmpName("Akshay");
		empRequest.setEmpId(1l);
		
		
		
		Mockito.when(employeeRepository.save(any(Employee.class))).thenThrow(EmployeeCreationExpection.class);
		
		
		assertThrows(EmployeeCreationExpection.class, () -> employeeService.addEmployee(empRequest));
		

	}

	
	@Test
	public void updateEmployeeTest() {

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

		Mockito.when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);
		Optional<Employee> savedEmployee = employeeService.updateEmployee(empRequest);
		Assertions.assertThat(savedEmployee.get()).isEqualTo(employee);
		verify(employeeRepository).save(any(Employee.class));
	}
	
	@Test
	public void updateEmployeeTestFail() {
		EmployeeRequest empRequest = new EmployeeRequest();
		empRequest.setCreatedAt(new Date());
		empRequest.setEmpGender("MALE");
		empRequest.setEmpRole("ASSOCIATE");
		empRequest.setEmpSalary(1000.0);
		empRequest.setUpdatedAt(new Date());
		empRequest.setEmpName("Akshay");
		empRequest.setEmpId(20l);
		
		Mockito.when(employeeRepository.save(any(Employee.class))).thenThrow(EmployeeUpdationFailed.class);
		assertThrows(EmployeeUpdationFailed.class, ()->employeeService.updateEmployee(empRequest));
		
	}

	@Test
	public void findByEmpIdTest() {

		Employee employee = new Employee();
		employee.setEmpGender(Gender.MALE);
		employee.setEmpId(1l);
		employee.setEmpName("akshay");
		employee.setEmpSalary(500000.00);
		employee.setCreatedAt(new Date());
		employee.setUpdatedAt(new Date());
		employee.setEmpRole(Role.ASSOCIATE);

		Optional<Employee> optemp = Optional.of(employee);
	
		

		Mockito.when(employeeRepository.findByEmpId(any())).thenReturn(optemp);
		Optional<Employee> actualResponse = employeeService.findByEmpId(1l);
		
		Assertions.assertThat(actualResponse).isEqualTo(optemp);
		verify(employeeRepository).findByEmpId(any());
		
	}
	
	@Test
	public void findByEmpIdTestFail() {
		Mockito.when(employeeRepository.findByEmpId(20l)).thenThrow(EmployeeNotFoundException.class);
		assertThrows(EmployeeNotFoundException.class, () -> employeeService.findByEmpId(20l));

	}
	
	@Test
	public void findAllTest() {
		List<Employee> employeeList= new ArrayList<Employee>();
		Employee employee1 = new Employee();
		employee1.setEmpGender(Gender.MALE);
		employee1.setEmpId(1l);
		employee1.setEmpName("akshay");
		employee1.setEmpSalary(500000.00);
		employee1.setCreatedAt(new Date());
		employee1.setUpdatedAt(new Date());
		employee1.setEmpRole(Role.ASSOCIATE);
		
		Employee employee2 = new Employee();
		employee2.setEmpGender(Gender.MALE);
		employee2.setEmpId(1l);
		employee2.setEmpName("akshay");
		employee2.setEmpSalary(500000.00);
		employee2.setCreatedAt(new Date());
		employee2.setUpdatedAt(new Date());
		employee2.setEmpRole(Role.ASSOCIATE);
		
		Employee employee3 = new Employee();
		employee3.setEmpGender(Gender.MALE);
		employee3.setEmpId(1l);
		employee3.setEmpName("akshay");
		employee3.setEmpSalary(500000.00);
		employee3.setCreatedAt(new Date());
		employee3.setUpdatedAt(new Date());
		employee3.setEmpRole(Role.ASSOCIATE);
		
		employeeList.add(employee1);
		employeeList.add(employee2);
		employeeList.add(employee3);
		
		Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
		
		List<Employee> emplist=employeeService.findAllEmployees();
		assertThat(emplist).isEqualTo(employeeList);
		
		
		
	}
	
	
	@Test
	public void findAllTestFail() {
		Mockito.when(employeeRepository.findAll()).thenThrow(NoEmployeeFoundException.class);
		
		assertThrows(NoEmployeeFoundException.class, ()-> employeeService.findAllEmployees());
		
	}
	
}