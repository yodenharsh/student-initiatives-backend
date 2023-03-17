package com.woxsen.studentinitiatives.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.User;
import com.woxsen.studentinitiatives.exceptions.InvalidCredentialsException;
import com.woxsen.studentinitiatives.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserREST {
	private UserService userService;
	
	
	public UserREST(@Autowired UserService userService) {
		this.userService = userService;
	}
	
	@CrossOrigin
	@PostMapping(value = "/user/", consumes = "application/json")
	public ResponseEntity<String> saveUser(@RequestBody @Valid User user) {
		System.out.println("Executed");
		userService.save(user);
		return ResponseEntity.ok("Added user");
	}
	
	@CrossOrigin
	@DeleteMapping("/user/")
	public ResponseEntity<String> deleteUser(@RequestBody User user) throws InvalidCredentialsException {
		userService.delete(user);
		return ResponseEntity.ok("Deleted User");
	}
	
	@CrossOrigin
	@PostMapping(value = "/user/login", consumes = "application/json")
	public Club login(@RequestBody User user) throws InvalidCredentialsException {
		Club club = userService.loginAndGetClubID(user);
		System.out.println(club);
		return club;
		
	}
}
