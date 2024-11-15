package com.jv.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jv.demo.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long>{

}
