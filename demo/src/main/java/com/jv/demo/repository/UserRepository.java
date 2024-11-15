package com.jv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jv.demo.model.Account;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {
	Account findByEmail(String email);
	
}
