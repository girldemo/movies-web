package com.jv.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jv.demo.model.Account;
import com.jv.demo.model.AccountProfile;
import com.jv.demo.model.Movie;
import com.jv.demo.model.Rating;
import com.jv.demo.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Account user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("Đăng ký thành công.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Account user) {
        Account existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null && userService.checkPassword(existingUser, user.getPassword())) {
            return ResponseEntity.ok("Đăng nhập thành công");
        }
        return ResponseEntity.badRequest().body("Mật khẩu hoặc email không đúng");
    }
    
    @PostMapping("/rating")
	public ResponseEntity<?> saveRating(@RequestBody Rating rating, @RequestParam Long movieId, @RequestParam String email) {
    	Rating savedRating = userService.saveRatingInMovieByUser(rating, movieId, email);
		return ResponseEntity.ok(savedRating);
	}

    @PutMapping("/rating")
	public ResponseEntity<?> updateRating(@RequestBody Rating rating, @RequestParam Long movieId, @RequestParam String email) {
    	Rating updatedRating = userService.updateRatingInMovieByUser(rating, movieId, email);
		return ResponseEntity.ok(updatedRating);
	}
    
    @GetMapping("/rating")
    public ResponseEntity<?> getRating(@RequestParam Long movieId, @RequestParam String email) {
    	Rating rating = userService.getRatingByMovieAndUser(movieId, email);
    	
    	return ResponseEntity.ok(rating);
    }
    
    @GetMapping("/rating/{email}")
    public ResponseEntity<List<Rating>> getRating(@PathVariable String email) {
    	return new ResponseEntity<List<Rating>>(userService.getRatingByUser(email), HttpStatus.OK);
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<Account> getUserByEmail(@PathVariable String email) {
    	return new ResponseEntity<Account>(userService.findByEmail(email), HttpStatus.OK);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody AccountProfile userProfile, @RequestParam Long id) {
    	AccountProfile updatedAccountProfile = userService.updateUserProfile(id, userProfile);
    	return ResponseEntity.ok(updatedAccountProfile);
    }
}
