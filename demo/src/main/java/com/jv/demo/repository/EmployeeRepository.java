package com.jv.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jv.demo.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
	List<Employee> findByName(String name);
	List<Employee> findByNameAndLocation(String name, String location);
	
	//SELECT * FROM TABLE WHERE name LIKE "%keyword%	
	List<Employee> findByNameContaining(String keyword, Sort sort);
	//List<Employee> findByNameLike(String "%"+keyword+"%");
	
	@Query("From Employee where name = :name or location = :location")
	List<Employee> getEmployeesByNameAndLocation(String name, String location);
	
	@Transactional
	@Modifying
	@Query("Delete from Employee where name = :name")
	Integer deleteEmployee(String name);
	
	List<Employee> findByDepartmentName(String name);
	
	@Query("from Employee where department.name = :name")
	List<Employee> getEmployeeByDepartmentName(String name);
	
}
