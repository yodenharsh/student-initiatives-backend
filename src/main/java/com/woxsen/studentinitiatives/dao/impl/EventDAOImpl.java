package com.woxsen.studentinitiatives.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.dao.EventDAO;
import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.ClubEvent;
import com.woxsen.studentinitiatives.entities.misc.EventAdd;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.storage.ImageProperties;
import com.woxsen.studentinitiatives.utility.DeleteFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class EventDAOImpl implements EventDAO {

	private EntityManager entityManager;
	private final Path rootLocation;
	private final Path rootLocationClassResource;
	
	@Autowired
	public EventDAOImpl(EntityManager entityManager,ImageProperties properties) {
		this.entityManager = entityManager;
		this.rootLocation = Paths.get(properties.getLocation());
		this.rootLocationClassResource = Paths.get(properties.getLocationClassResource());
	}
	
	@Override
	public List<ClubEvent> findAll() {
		
		Session session = entityManager.unwrap(Session.class);
		
		String HQLQuery = "from ClubEvent";
		
		List<ClubEvent> events = session.createSelectionQuery(HQLQuery, ClubEvent.class).getResultList();
		
		return events;
	}

	@Override
	public List<ClubEvent> findByClubID(int clubId) {
		Session session = entityManager.unwrap(Session.class);
		
		Club club = session.get(Club.class, clubId);
		
		if(club == null)throw new EntityNotFoundException("No club with clubId="+clubId+" was found");
		
		SelectionQuery<ClubEvent> q = session.createSelectionQuery("from Event e where e.club=:p", ClubEvent.class);
		q.setParameter("p", club);
		
		List<ClubEvent> events = q.getResultList();
		
		return events;
	}

	@Override
	public ClubEvent findByEventId(int eventId) {
		Session session = entityManager.unwrap(Session.class);
		
		ClubEvent event = session.get(ClubEvent.class, eventId);
		if(event == null) throw new EntityNotFoundException("No event with eventId="+eventId+ " was found");
		
		return event;
	}

	@Override
	public int add(EventAdd event) {
		Session session = entityManager.unwrap(Session.class);
		
		Club club = session.get(Club.class, event.getClubId());
		if(club == null) throw new EntityNotFoundException("No event with that club exists");
		
		ClubEvent eventToAdd = new ClubEvent(event.getTitle(), event.getEventDesc(), event.getDate());
		eventToAdd.setClub(club);
		
		session.persist(eventToAdd);
		
		return eventToAdd.getEventId();
	}

	@Override
	public int remove(int eventId) {
		Session session = entityManager.unwrap(Session.class);
		
		ClubEvent event = session.get(ClubEvent.class, eventId);
		if(event == null) throw new EntityNotFoundException("No event with eventId="+eventId+" was found");
		
		
		session.remove(event);
		
		return eventId;
	}

	@Override
	public int update(int eventId, EventAdd event) {
		Session session = entityManager.unwrap(Session.class);
		
		ClubEvent eventInDB = session.get(ClubEvent.class, eventId);
		
		if(eventInDB == null) throw new EntityNotFoundException("No event with eventId="+eventId+" was found");
		
		eventInDB.setDate(event.getDate());
		eventInDB.setEventDesc(event.getEventDesc());
		eventInDB.setTitle(event.getTitle());
		
		return eventInDB.getEventId();
		
	}

	@Override
	public void saveImage(MultipartFile file, int clubId, int eventId) {
		String MIMEType = file.getContentType();
		String extension = MIMEType.substring(MIMEType.indexOf("/")+ 1);
		
		String[] extensions = {".jpeg",".png",".jpg"};

		
		String fileName = eventId + "." + extension;
		
		DeleteFile.deleteFile(String.valueOf(eventId), rootLocation.toString()+"/club/events/" + clubId + "/", extensions);
		
		Path finalLocation = Paths.get(rootLocation.toString()+"/club/events/" + clubId + "/");
		if(!Files.exists(finalLocation))
			try {
				Files.createDirectories(finalLocation);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		try(InputStream inputStream = file.getInputStream()){
			Files.copy(inputStream, finalLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);		
		} catch(IOException e) {
		e.printStackTrace(); 
		}
	}

	@Override
	public InputStreamResource getImage(int clubId, int eventId) throws NoSuchFileFoundException {
		var imgFile = new ClassPathResource(rootLocationClassResource + "/club/events/" +clubId+ "/" + eventId + ".jpeg");
		if(!imgFile.exists()) imgFile = new ClassPathResource(rootLocationClassResource + "/clubs/events/" +clubId+ "/" + eventId+".jpg");
		if(!imgFile.exists()) imgFile = new ClassPathResource(rootLocationClassResource + "/clubs/events/" +clubId+ "/" + eventId+".png");
		if(!imgFile.exists()) throw new NoSuchFileFoundException("Image with clubId="+clubId + " and eventId="+eventId+" was not found");
		try {
			return new InputStreamResource(imgFile.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			throw new NoSuchFileFoundException(imgFile + " doesn't exist!");
		}
	}

}
