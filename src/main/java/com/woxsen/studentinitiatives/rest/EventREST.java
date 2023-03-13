package com.woxsen.studentinitiatives.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.woxsen.studentinitiatives.entities.ClubEvent;
import com.woxsen.studentinitiatives.entities.misc.EventAdd;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.service.EventService;

@RestController
@RequestMapping("/api")
public class EventREST {

	private EventService eventService;
	
	@Autowired
	public EventREST(EventService eventService) {
		this.eventService = eventService;
	}
	
	@CrossOrigin
	@GetMapping("/event")
	public List<ClubEvent> getAll(){
		return eventService.findAll();
	}
	
	@CrossOrigin
	@PostMapping("/event")
	public ResponseEntity<HashMap<String, Integer>> addEvent(@RequestBody EventAdd eventAdd){
		int eventId = eventService.add(eventAdd);

		HashMap<String, Integer> response = new HashMap<>();
		response.put("eventId", eventId);
		
		return ResponseEntity.ok().body(response);
	}
	
	@CrossOrigin
	@GetMapping("/event/club/{clubId}")
	public List<ClubEvent> findByClubId(@PathVariable int clubId){
		return eventService.findByClubID(clubId);
	}
	
	@CrossOrigin
	@GetMapping("/event/{eventId}")
	public ClubEvent findByEventId(@PathVariable int eventId) {
		return eventService.findByEventId(eventId);
	}
	
	@CrossOrigin
	@DeleteMapping("/event/{eventId}")
	public ResponseEntity<String> delete(@PathVariable int eventId){
		
		eventService.remove(eventId);
		return ResponseEntity.ok("Event with id="+eventId+" was deleted");
	}
	
	@CrossOrigin
	@PatchMapping("/event/{eventId}")
	public ResponseEntity<String> update(@RequestBody EventAdd event, @PathVariable int eventId){
		eventService.update(eventId, event);
		
		return ResponseEntity.ok("Event with id="+eventId+" was updated");
		
	}
	
	@CrossOrigin
	@GetMapping(value = "/event/image/{clubId}/{eventId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public ResponseEntity<InputStreamResource> getImage(@PathVariable int clubId, @PathVariable int eventId) throws NoSuchFileFoundException{
		return ResponseEntity.ok(eventService.getImage(clubId, eventId));
	}
	
	@CrossOrigin
	@PutMapping(value = "/event/image/{clubId}/{eventId}")
	public ResponseEntity<String> saveImage(@RequestParam("image") MultipartFile image, @PathVariable int clubId, @PathVariable int eventId){
		eventService.saveImage(image, clubId, eventId);
		
		return ResponseEntity.ok("Image saved");
	}
}
