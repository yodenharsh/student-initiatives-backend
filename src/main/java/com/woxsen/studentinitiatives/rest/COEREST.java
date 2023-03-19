package com.woxsen.studentinitiatives.rest;

import java.nio.file.NoSuchFileException;
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

import com.woxsen.studentinitiatives.entities.COE;
import com.woxsen.studentinitiatives.exceptions.InvalidTypeException;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.service.COEService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class COEREST {

	private COEService coeService;

	@Autowired
	public COEREST(COEService coeService) {
		this.coeService = coeService;
	}

	@GetMapping("/coe/{id}")
	public ResponseEntity<COE> getCOE(@PathVariable int id) {
		return ResponseEntity.ok(coeService.findById(id));
	}

	@GetMapping("/coe")
	public ResponseEntity<List<COE>> getAllCOEs() {
		return ResponseEntity.ok(coeService.findAll());
	}

	@PostMapping("/coe/{coFounderEmail}")
	public ResponseEntity<HashMap<String, String>> addCOE(@RequestBody COE coe, @PathVariable String coFounderEmail) {
		int id = coeService.add(coe, coFounderEmail);

		HashMap<String, String> response = new HashMap<>();

		response.put("id", String.valueOf(id));
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/coe/{id}")
	public ResponseEntity<COE> editCOE(@RequestBody COE coe, @PathVariable int id) {
		COE editedCOE = coeService.patchById(id, coe);

		return ResponseEntity.ok(editedCOE);

	}

	@DeleteMapping("/coe/{id}")
	public ResponseEntity<HashMap<String, String>> deleteCOE(@PathVariable int id) {
		int deletedCOEId = coeService.deleteById(id);

		HashMap<String, String> response = new HashMap<String, String>();
		response.put("id", String.valueOf(deletedCOEId));
		response.put("success", "true");

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/coe/{coeId}/image/{type}", produces = { MediaType.IMAGE_PNG_VALUE,
			MediaType.IMAGE_JPEG_VALUE })
	public ResponseEntity<InputStreamResource> getImage(@PathVariable int coeId, @PathVariable String type)
			throws InvalidTypeException, NoSuchFileException, NoSuchFileFoundException {
		if (!(type.equals("logo") || type.equals("cofounder")))
			throw new InvalidTypeException("Type should either be 'logo' or 'cofounder'");

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(coeService.getImage(coeId, type));
	}

	@PutMapping(value = "/coe/{coeId}/image/{type}")
	public ResponseEntity<HashMap<String, String>> saveImage(@RequestParam("image") MultipartFile file,
			@PathVariable int coeId, @PathVariable String type) throws InvalidTypeException {
		if (!(type.equals("logo") || type.equals("cofounder")))
			throw new InvalidTypeException("Type should either be 'logo' or 'cofounder'");

		HashMap<String, String> response = new HashMap<>();
		coeService.saveImage(coeId, type, file);

		response.put("status", "Success");
		return ResponseEntity.ok(response);

	}
}
