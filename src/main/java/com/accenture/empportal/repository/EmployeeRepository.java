package com.accenture.empportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.empportal.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	
	public Optional<Employee> findByEmpId(Long id);
}
