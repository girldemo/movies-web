package com.jv.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jv.demo.model.Employee;
import com.jv.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository eRepository;
	
	@Override
	public List<Employee> getEmployees(int pageNumber, int pageSize) {
		Pageable pages = PageRequest.of(pageNumber, pageSize, Direction.DESC, "id");
		return eRepository.findAll(pages).getContent();
	}

	@Override
	public Employee saveEmployee(Employee e) {
		return eRepository.save(e);
	}

	@Override
	public Employee getSingleEmployee(Long id) {
		Optional<Employee> e = eRepository.findById(id);
		if (e.isPresent()) {
			return e.get();
		}
		throw new RuntimeException("not found id " + id);
	}

	@Override
	public void deleteEmployee(Long id) {
		eRepository.deleteById(id);
	}

	@Override
	public Employee updateEmployee(Employee e) {
		return eRepository.save(e);
	}

	@Override
	public List<Employee> getEmployeesByName(String name) {
		return eRepository.findByName(name);
	}

	@Override
	public List<Employee> getEmployeesByNameAndLocation(String name, String location) {
		return eRepository.findByNameAndLocation(name, location);
	}

	@Override
	public List<Employee> getEmployeesByKeyword(String name) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		return eRepository.findByNameContaining(name, sort);
	}

	@Override
	public List<Employee> getEmployeesByNameOrLocation(String name, String location) {
		return eRepository.getEmployeesByNameAndLocation(name, location);
	}

	@Override
	public Integer deleteByEmployeeName(String name) {
		return eRepository.deleteEmployee(name);
	}

	@Override
	public List<Employee> getEmployeeByDepartmentName(String name) {
		return eRepository.findByDepartmentName(name);
	}

}
