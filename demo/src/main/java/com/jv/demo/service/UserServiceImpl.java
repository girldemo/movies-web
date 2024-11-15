package com.jv.demo.service;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.jv.demo.model.Account;
import com.jv.demo.model.AccountProfile;
import com.jv.demo.model.Movie;
import com.jv.demo.model.Rating;
import com.jv.demo.model.Role;
import com.jv.demo.repository.MovieRepository;
import com.jv.demo.repository.RatingRepository;
import com.jv.demo.repository.RoleRepository;
import com.jv.demo.repository.UserProfileRepository;
import com.jv.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private UserProfileRepository userProfileRepo;
	
	@Autowired 
	private RatingRepository ratingRepo;
	
	@Autowired
	private MovieRepository movieRepo;

	@Override
	public Account saveUser(Account user) {
		//password
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		
		//user profile
		AccountProfile profile = new AccountProfile();
		profile.setFullName("Full Name");
		profile.setGender(0);
		profile.setAddress("Address");
		profile.setPhoneNumber("123456");
		profile.setAvatar("https://s1.zerochan.net/Kuchiki.Toko.600.3727902.jpg");
		user.setAccountProfile(profile);
		
		//role
		Role existingRole = roleRepo.findByName("USER");
		
		if (existingRole == null) {
			Role role = new Role();
			role.setName("USER");
			role.setDescription("this is user");
			user.setRole(role);
			return userRepo.save(user);
		}
	
		user.setRole(existingRole);
		return userRepo.save(user);
	}

	@Override
	public Account findByEmail(String email) {
		return userRepo.findByEmail(email);
	}	
	
	@Override
	public boolean checkPassword(Account user, String Password) {
		return BCrypt.checkpw(Password, user.getPassword());
	}

	@Override
	public Rating saveRatingInMovieByUser(Rating rating, long movieId, String email) {
		Account user = userRepo.findByEmail(email);
        Movie movie = movieRepo.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        rating.setAccount(user);
        rating.setMovie(movie);

        return ratingRepo.save(rating);
	}

	@Override
	public Rating getRatingByMovieAndUser(long movieId, String email) {
		Account account = userRepo.findByEmail(email);
		Movie movie = movieRepo.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));	
		
		return ratingRepo.findByAccountAndMovie(account, movie);
	}

	@Override
	public List<Rating> getRatingByUser(String email) {
		Account account = userRepo.findByEmail(email);
		
		return ratingRepo.findByAccount(account);
	}

	@Override
	public Rating updateRatingInMovieByUser(Rating rating, long movieId, String email) {
		Account account = userRepo.findByEmail(email);
		Movie movie = movieRepo.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));	
		
		Rating existingRating = ratingRepo.findByAccountAndMovie(account, movie);
		if (existingRating != null) {
			existingRating.setStars(rating.getStars());
			existingRating.setRatingDate(rating.getRatingDate());
			
			return ratingRepo.save(existingRating);
		}
		else {
            throw new RuntimeException("Rating not found");
        }
	}

	@Override
	public AccountProfile updateUserProfile(Long id, AccountProfile accountProfile) {
		Optional<AccountProfile> existingAccountProfile = userProfileRepo.findById(id);
		if (existingAccountProfile.isPresent()) {
			existingAccountProfile.get().setFullName(accountProfile.getFullName());
			existingAccountProfile.get().setPhoneNumber(accountProfile.getPhoneNumber());
			existingAccountProfile.get().setAddress(accountProfile.getAddress());
			existingAccountProfile.get().setGender(accountProfile.getGender());
			
			return userProfileRepo.save(existingAccountProfile.get());
		}
		else {
			throw new RuntimeException("UserProfile not found");
		}
	}
	
}
