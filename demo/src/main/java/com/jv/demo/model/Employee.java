package com.jv.demo.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	//@JsonProperty("full_name")
	@Column(name = "name")
	@NotBlank
	private String name;
	
	//@JsonIgnore
	@Column(name = "age")
	private Long age = 0L;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "email")
	@Email
	private String email;
	
//	@Column(name = "department")
//	@NotBlank
//	private String department;
	@JoinColumn(name = "department_id")
	@OneToOne
	private Department department;
	
	@CreationTimestamp
	@Column(name = "create_at", nullable = false, updatable = false)
	private Date createAt;
	
	@UpdateTimestamp
	@Column(name = "update_at")
	private Date updateAt;
}
