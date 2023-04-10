package com.woxsen.studentinitiatives.rest;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woxsen.studentinitiatives.entities.Admin;
import com.woxsen.studentinitiatives.exceptions.InvalidCredentialsException;
import com.woxsen.studentinitiatives.service.AdminService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdminREST {
	
	private AdminService adminService;

	@Autowired
	public AdminREST(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HashMap<String, String>> addAdmin(@RequestBody Admin admin){
		HashMap<String, String> response = new HashMap<>();
		
		response.put("success", String.valueOf(adminService.addAdmin(admin)));
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value = "/admin/login")
	public ResponseEntity<HashMap<String, String>> loginAdmin(@RequestBody Admin admin) throws InvalidCredentialsException{
		HashMap<String, String> response = new HashMap<>();
		
		response.put("success", String.valueOf(adminService.login(admin)));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/admin")
	public ResponseEntity<HashMap<String, String>> deleteAdmin(@RequestBody Admin admin) throws InvalidCredentialsException{
		HashMap<String, String> response = new HashMap<>();
		
		response.put("success", String.valueOf(adminService.removeAdmin(admin)));
		return ResponseEntity.ok(response);
	}
}
