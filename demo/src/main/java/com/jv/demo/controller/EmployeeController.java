package com.jv.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jv.demo.model.Employee;
import com.jv.demo.repository.EmployeeRepository;
import com.jv.demo.service.EmployeeService;

import jakarta.validation.Valid;

//@Controller
@RestController // @Controller + @ResponseBody
//@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService eService;
	
	@Autowired
	private EmployeeRepository eRepo;
	
	@Value("${spring.application.name}")
	private String appName;

	@Value("${spring.application.version}")
	private String appVersion;
	
	@GetMapping("/version")
	public String appInfor() {
		return appName + "-" + appVersion;
	}
	
//	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	@GetMapping("/employees")
	public List<Employee> getEmployees(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return eService.getEmployees(pageNumber, pageSize);
	}
	
	@GetMapping("/employees/{id}")
	public Employee getEmployee(@PathVariable("id") Long id) {
		return eService.getSingleEmployee(id);
	}
	
	@PostMapping("/employees")
	public Employee saveEmployee(@Valid @RequestBody Employee e) {
		return eService.saveEmployee(e);
	}
	
	@PutMapping("/employees/{id}")
	public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee e) {
		e.setId(id);
		return eService.saveEmployee(e);
	}
	
	@DeleteMapping("/employees")
	public void deleteEmployee(@RequestParam Long id) {
		eService.deleteEmployee(id);
	}
	
	@GetMapping("/employees/filterByName")
	public ResponseEntity<List<Employee>> getEmployeesByName(@RequestParam String name) {
		return new ResponseEntity<List<Employee>>(eService.getEmployeesByName(name), HttpStatus.OK);
	}
	
	@GetMapping("/employees/filterByNameAndLocation")
	public ResponseEntity<List<Employee>> getEmployeesByName(@RequestParam String name, @RequestParam String location) {
		return new ResponseEntity<List<Employee>>(eService.getEmployeesByNameAndLocation(name, location), HttpStatus.OK);
	}
	
	@GetMapping("/employees/filterByKeyword")
	public ResponseEntity<List<Employee>> getEmployeesByKeyword(@RequestParam String name) {
		return new ResponseEntity<List<Employee>>(eService.getEmployeesByKeyword(name), HttpStatus.OK);
	}
	
	@GetMapping("/employees/{name}/{location}")
	public ResponseEntity<List<Employee>> getEmployeesByNameOrLocation(@PathVariable String name, @PathVariable String location) {
		return new ResponseEntity<List<Employee>>(eService.getEmployeesByNameOrLocation(name, location), HttpStatus.OK);
	}
	
	@DeleteMapping("/employees/delete/{name}")
	public ResponseEntity<String> deleteEmployeeByName(@PathVariable String name) {
		return new ResponseEntity<String>(eService.deleteByEmployeeName(name) + "No. of record deleted!!	", HttpStatus.OK);
	}
	
//	@GetMapping("/employees/filter/{departmentName}")
//	public ResponseEntity<List<Employee>> getEmployeeByDepartmentName(@PathVariable String departmentName) {
//		return new ResponseEntity<List<Employee>>(eService.getEmployeeByDepartmentName(departmentName), HttpStatus.OK);
//	}
	@GetMapping("/employees/filter/{departmentName}")
	public ResponseEntity<List<Employee>> getEmployeeByDepartmentName(@PathVariable String departmentName) {
		return new ResponseEntity<List<Employee>>(eRepo.getEmployeeByDepartmentName(departmentName), HttpStatus.OK);
	}
}
