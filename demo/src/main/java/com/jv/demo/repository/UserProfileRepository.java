package com.jv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jv.demo.model.AccountProfile;

public interface UserProfileRepository extends JpaRepository<AccountProfile, Long>{

}
