package com.accenture.empportal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.accenture.empportal.exception.EmployeeNotFoundException;
import com.accenture.empportal.repository.EmployeeRepository;
import com.accenture.empportal.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EmpServiceTest {
	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepository employeeRepository;
	
	
	
	@Test
	void addEmployeetestFail() {
		
		given(employeeRepository.findByEmpId(20l)).willThrow(EmployeeNotFoundException.class);
		
		//Mockito.when(employeeRepository.findByEmpId(20l)).thenThrow(EmployeeNotFoundException.class);
		//assertThrows(EmployeeNotFoundException.class, () -> employeeService.findByEmpId(20l));

		assertThatThrownBy(() ->  employeeService.findByEmpId(20l))
        .isInstanceOf(EmployeeNotFoundException.class);
		
		
	}
	
}

