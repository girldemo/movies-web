package com.jv.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jv.demo.model.Account;
import com.jv.demo.model.AccountProfile;
import com.jv.demo.model.Rating;

@Service
public interface UserService {

	Account saveUser(Account user);
	
	Account findByEmail(String email);
	
	boolean checkPassword(Account user, String Password);
	
	Rating saveRatingInMovieByUser(Rating rating, long movieId, String email);
	
	Rating getRatingByMovieAndUser(long movieId, String email);
	
	List<Rating> getRatingByUser(String email);
	
	Rating updateRatingInMovieByUser(Rating rating, long movieId, String email);
	
	AccountProfile updateUserProfile(Long id, AccountProfile accountProfile);
}
