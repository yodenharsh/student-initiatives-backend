package com.woxsen.studentinitiatives.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.misc.ClubAdd;
import com.woxsen.studentinitiatives.exceptions.InvalidTypeException;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.service.ClubService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
public class ClubREST {

	private ClubService clubService;

	@Autowired
	public ClubREST(ClubService clubService) {
		this.clubService = clubService;
	}

	@CrossOrigin
	@GetMapping(value = "/club/{clubId}", produces = "application/json")
	public Club getClubById(@PathVariable int clubId) {
		Club club = clubService.findById(clubId);
		if (club == null)
			throw new EntityNotFoundException("Any club with ID=" + clubId + " was not found!");

		return club;
	}

	@CrossOrigin
	@GetMapping(value = "/club/")
	public List<Club> getClubs() {
		return clubService.findAll();
	}

	@CrossOrigin
	@DeleteMapping(value = "/club/{clubId}")
	public ResponseEntity<String> deleteClub(@PathVariable int clubId) {
		try {
			clubService.deleteById(clubId);
			return ResponseEntity.ok("Club deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}

	@CrossOrigin
	@PostMapping(value = "/club/")
	public ResponseEntity<HashMap<String, Integer>> addClub(@RequestBody ClubAdd club) {
		int clubId = clubService.add(club);
		HashMap<String, Integer> map = new HashMap<>();
		map.put("clubId", clubId);
		return ResponseEntity.ok(map);
	}

	@CrossOrigin
	@GetMapping(value = "/club/{clubId}/image/{type}", produces = { MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<InputStreamResource> getImage(@PathVariable int clubId, @PathVariable String type)
			throws NoSuchFileFoundException, InvalidTypeException {
		if (!(type.equals("logo") || type.equals("president") || type.equals("vice-president")))
			throw new InvalidTypeException(type + " is not a valid type");
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(clubService.getImage(clubId, type));
	}

	@CrossOrigin
	@PutMapping(value = "/club/{clubId}/image/{type}")
	public ResponseEntity<String> saveImage(@RequestParam("image") MultipartFile file, @PathVariable int clubId,
			@PathVariable String type) throws InvalidTypeException {
		if (!(type.equals("logo") || type.equals("president") || type.equals("vice-president")))
			throw new InvalidTypeException(type + " is not a valid type");

		clubService.saveImage(clubId, type, file);
		return ResponseEntity.ok().body("Image saved successfully");
	}
}
