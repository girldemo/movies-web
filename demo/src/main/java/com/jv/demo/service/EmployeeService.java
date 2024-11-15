package com.jv.demo.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.jv.demo.model.Employee;

public interface EmployeeService {
	
	List<Employee> getEmployees(int pageNumber, int pageSize);
	
	Employee saveEmployee(Employee e);
	
	Employee getSingleEmployee(Long id);
	
	void deleteEmployee(Long id);
	
	Employee updateEmployee(Employee e);
	
	List<Employee> getEmployeesByName(String name);
	
	List<Employee> getEmployeesByNameAndLocation(String name, String location);
	
	List<Employee> getEmployeesByKeyword(String name);
	
	List<Employee> getEmployeesByNameOrLocation(String name, String location);
	
	Integer deleteByEmployeeName(String name);
	
	List<Employee> getEmployeeByDepartmentName(String name);
}
