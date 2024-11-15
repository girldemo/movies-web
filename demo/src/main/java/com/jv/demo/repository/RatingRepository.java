package com.jv.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jv.demo.model.Account;
import com.jv.demo.model.Movie;
import com.jv.demo.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{
	
	Rating findByAccountAndMovie(Account account, Movie movie);
	
	List<Rating> findByAccount(Account account);
}
