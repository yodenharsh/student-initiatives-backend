package com.woxsen.studentinitiatives.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.woxsen.studentinitiatives.entities.COEPosts;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.service.COEPostsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class COEPostsREST {

	private COEPostsService coePostsService;

	@Autowired
	public COEPostsREST(COEPostsService coePostsService) {
		this.coePostsService = coePostsService;
	}

	@PostMapping(value = "/coe-post/{coeId}/")
	public ResponseEntity<HashMap<String, String>> addCOEPost(@RequestBody COEPosts coePost, @PathVariable int coeId) {
		HashMap<String, String> response = new HashMap<>();
		int coePostId = coePostsService.addCOEPost(coePost, coeId);
		response.put("coePostId", String.valueOf(coePostId));

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/coe-post/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<COEPosts> findAll() {
		List<COEPosts> coePosts = coePostsService.findAll();
		return coePosts;
	}

	@GetMapping(value = "/coe-post/coe/{coeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MappingJacksonValue findByCOEId(@PathVariable int coeId) {
		List<COEPosts> coePosts = coePostsService.findByCOEId(coeId);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "title", "desc", "date");
		FilterProvider filters = new SimpleFilterProvider().addFilter("noCOEFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(coePosts);
		mapping.setFilters(filters);
		return mapping;
	}

	@GetMapping(value = "/coe-post/{coePostId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public COEPosts findByCOEPostId(@PathVariable int coePostId) {
		return coePostsService.findById(coePostId);
	}

	@PatchMapping(value = "/coe-post/{coePostId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public COEPosts updateCOEPost(@RequestBody COEPosts coePost, @PathVariable int coePostId) {
		return coePostsService.patchCOEPost(coePost, coePostId);
	}

	@DeleteMapping(value = "/coe-post/{coePostId}")
	public ResponseEntity<HashMap<String, String>> deleteCOEPost(@PathVariable int coePostId) {
		HashMap<String, String> response = new HashMap<>();
		response.put("success", String.valueOf(coePostsService.deleteCOEPostById(coePostId)));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/coe-post/{coeId}/image/{coePostId}", produces = { MediaType.IMAGE_PNG_VALUE,
			MediaType.IMAGE_JPEG_VALUE })
	public ResponseEntity<InputStreamResource> getImage(@PathVariable int coeId, @PathVariable int coePostId)
			throws NoSuchFileFoundException {
		return ResponseEntity.ok(coePostsService.getImage(coeId, coePostId));
	}

	@PutMapping(value = "/coe-post/{coeId}/image/{coePostId}")
	public ResponseEntity<HashMap<String, String>> addImage(@RequestParam("image") MultipartFile file, @PathVariable int coeId,
			@PathVariable int coePostId) {
		HashMap<String, String> response = new HashMap<>();
		coePostsService.saveImage(file, coeId, coePostId);
		response.put("success","true");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/coe-post/{coeId}/file/{coePostId}")
	public ResponseEntity<InputStreamResource> getFile(@PathVariable int coeId, @PathVariable int coePostId, HttpServletRequest request) throws NoSuchFileFoundException{
		
		InputStreamResource file = coePostsService.getFile(coePostId, coeId);
		
		String contentType = null;
		
		contentType = request.getServletContext().getMimeType(file.getDescription().substring(file.getDescription().indexOf('[')+1, file.getDescription().indexOf(']')));
	     if(contentType == null) {
	    	 contentType = "application/octet-stream";
	    }
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(file);
	}
	
	@PutMapping(value = "/coe-post/{coeId}/file/{coePostId}")
	public ResponseEntity<HashMap<String, String>> addFile(@RequestParam("file") MultipartFile file, @PathVariable int coeId, @PathVariable int coePostId){
		HashMap<String, String> response = new HashMap<>();
		response.put("success", "true");
		coePostsService.saveFile(file, coeId, coePostId);
		return ResponseEntity.ok(response);
	}
}
