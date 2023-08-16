package com.woxsen.studentinitiatives.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.dao.EventDAO;
import com.woxsen.studentinitiatives.entities.ClubEvent;
import com.woxsen.studentinitiatives.entities.misc.EventAdd;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.service.EventService;

import jakarta.transaction.Transactional;

@Service
public class EventServiceImpl implements EventService {

	private EventDAO eventDAO;
	
	@Autowired
	public EventServiceImpl(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}
	
	@Override
	@Transactional
	public List<ClubEvent> findAll() {
		return eventDAO.findAll();
	}

	@Override
	@Transactional
	public List<ClubEvent> findByClubID(int clubId) {
		return eventDAO.findByClubID(clubId);
	}

	@Override
	@Transactional
	public ClubEvent findByEventId(int eventId) {
		return eventDAO.findByEventId(eventId);
	}
	
	@Override
	@Transactional
	public List<ClubEvent> findByDateRange(LocalDate start, LocalDate end){
		
		LocalDateTime startConverted = start.atStartOfDay();
		LocalDateTime endConverted = end.atTime(23, 59, 59);
		
		return eventDAO.findByDateRange(startConverted, endConverted);
	}

	@Override
	@Transactional
	public int add(EventAdd event) {
		return eventDAO.add(event);
	}

	@Override
	@Transactional
	public int remove(int eventId) {
		return eventDAO.remove(eventId);
	}

	@Override
	@Transactional
	public int update(int eventId, EventAdd event) {
		return eventDAO.update(eventId, event);
	}

	@Override
	public void saveImage(MultipartFile file, int clubId, int eventId) {
		eventDAO.saveImage(file, clubId, eventId);
	}

	@Override
	public InputStreamResource getImage(int clubId, int eventId) throws NoSuchFileFoundException {
		return eventDAO.getImage(clubId, eventId);
	}

}
