package com.woxsen.studentinitiatives.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.School;
import com.woxsen.studentinitiatives.service.SchoolService;

@RestController
@RequestMapping("/api")
public class SchoolREST {

	private SchoolService schoolService;
	
	@Autowired
	public SchoolREST(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
	
	@CrossOrigin
	@PostMapping(value = "/school/")
	public ResponseEntity<HashMap<String, String>> addSchool(@RequestBody School school) {
		schoolService.add(school);
		HashMap<String,String> response = new HashMap<>();
		response.put("success", "true");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@CrossOrigin
	@DeleteMapping(value = "/school/{schoolId}")
	public ResponseEntity<HashMap<String, String>> deleteSchool(int schoolId){
		schoolService.removeById(schoolId);
		HashMap<String,String> response = new HashMap<>();
		response.put("success", "true");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@CrossOrigin
	@GetMapping(value = "/school/{schoolId}/clubs")
	public List<Club> getClubList(@PathVariable int schoolId){
		return schoolService.getClubListBySchoolId(schoolId);
	}
	
	@CrossOrigin
	@GetMapping(value = "/school/{schoolId}", produces = "application/json")
	public School getSchoolById(@PathVariable int schoolId) {
		return schoolService.getById(schoolId);
	}
	
	@CrossOrigin
	@GetMapping(value = "/school/")
	public List<School> getSchools(){
		return schoolService.getSchools();
	}
}
