package com.woxsen.studentinitiatives.service;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.entities.ClubEvent;
import com.woxsen.studentinitiatives.entities.misc.EventAdd;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;

public interface EventService {
	public List<ClubEvent> findAll();

	public List<ClubEvent> findByClubID(int clubId);
	
	public ClubEvent findByEventId(int eventId);
	
	public int add(EventAdd event);
	
	public int remove(int eventId);
	
	public int update(int eventId, EventAdd event);
	
	public void saveImage(MultipartFile file, int clubId, int eventId);
	
	public InputStreamResource getImage(int clubId, int eventId) throws NoSuchFileFoundException;
}
