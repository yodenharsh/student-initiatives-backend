package com.woxsen.studentinitiatives.service;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.misc.ClubAdd;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;

public interface ClubService {
			
	public Club findById(int clubId);
	
	public List<Club> findAll();
	
	public int add(ClubAdd club);
	
	public void deleteById(int clubId);
	
	public InputStreamResource getImage(int clubId, String type) throws NoSuchFileFoundException;
	
	public void saveImage(int clubId, String type, MultipartFile file);
}
