package com.woxsen.studentinitiatives.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<HashMap<String, String>> saveUser(@RequestBody @Valid User user) {
		System.out.println("Executed");
		userService.save(user);
		HashMap<String, String> response = new HashMap<>();
		response.put("success",	"true");
		return ResponseEntity.ok(response);
	}
	
	@CrossOrigin
	@DeleteMapping("/user/")
	public ResponseEntity<HashMap<String,String>> deleteUser(@RequestBody User user) throws InvalidCredentialsException {
		userService.delete(user);
		HashMap<String, String> response = new HashMap<>();
		response.put("success", "true");
		return ResponseEntity.ok(response);
	}
	
	@CrossOrigin
	@PostMapping(value = "/user/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Integer> login(@RequestBody User user) throws InvalidCredentialsException {
		Map<String, Integer> responseMap = userService.loginAndGetID(user);
		return responseMap;
		
	}
}
