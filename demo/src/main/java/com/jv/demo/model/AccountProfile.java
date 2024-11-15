package com.jv.demo.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_profiles")
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountProfile extends BaseEntity{
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(columnDefinition = "tinyint(5)")
	private int gender;
	
	@Column
	private String address;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column
	private String avatar;
}
