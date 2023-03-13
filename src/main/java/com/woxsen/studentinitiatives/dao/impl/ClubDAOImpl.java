package com.woxsen.studentinitiatives.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.dao.ClubDAO;
import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.School;
import com.woxsen.studentinitiatives.entities.User;
import com.woxsen.studentinitiatives.entities.misc.ClubAdd;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.storage.ImageProperties;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class ClubDAOImpl implements ClubDAO {

	private EntityManager entityManager;
	private final Path rootLocation;
	private final Path rootLocationClassResource;

	@Autowired
	public ClubDAOImpl(EntityManager entityManager, ImageProperties properties) {
		this.entityManager = entityManager;
		this.rootLocation = Paths.get(properties.getLocation());
		this.rootLocationClassResource = Paths.get(properties.getLocationClassResource());
	}

	@PostConstruct
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
			Files.createDirectories(rootLocationClassResource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Club findById(int clubId) {
		Session session = entityManager.unwrap(Session.class);

		Club club = session.get(Club.class, clubId);

		if (club == null)
			throw new EntityNotFoundException("No club with clubId=" + clubId + " exists");

		return club;
	}

	@Override
	public List<Club> findAll() {
		Session session = entityManager.unwrap(Session.class);

		List<Club> clubs = session.createSelectionQuery("from Club", Club.class).getResultList();
		return clubs;
	}

	@Override
	public void deleteById(int clubId) {
		Session session = entityManager.unwrap(Session.class);
		Club club = session.get(Club.class, clubId);
		if (club == null)
			throw new EntityNotFoundException();
		session.remove(club);
	}

	@Override
	public int add(ClubAdd club) {
		Session session = entityManager.unwrap(Session.class);

		School school = session.get(School.class, club.getSchoolId());
		User user = session.get(User.class, club.getEmail());

		if (school == null)
			throw new EntityNotFoundException("No school with schoolId=" + club.getSchoolId() + " exists");
		if (user == null)
			throw new EntityNotFoundException("No user with email=" + club.getEmail() + " exists");

		Club clubToAdd = new Club(club.getClubName(), club.getPresidentName(), club.getVicePresidentName(),
				club.getMission(), club.getVision());
		clubToAdd.setSchool(school);
		clubToAdd.setUser(user);

		session.persist(clubToAdd);

		return clubToAdd.getClubId();

	}

	@Override
	public InputStreamResource getImage(int clubId, String type) throws NoSuchFileFoundException {
		var imgFile = new ClassPathResource(rootLocationClassResource + "/club/" + type + "/" + clubId + ".jpeg");
		if (!imgFile.exists())
			imgFile = new ClassPathResource(rootLocationClassResource + "/club/" + type + "/" + clubId + ".jpg");
		if (!imgFile.exists())
			imgFile = new ClassPathResource(rootLocationClassResource + "/club/" + type + "/" + clubId + ".png");
		if (!imgFile.exists())
			throw new NoSuchFileFoundException("The given clubId with that type was not found");
		try {
			return new InputStreamResource(imgFile.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			throw new NoSuchFileFoundException(imgFile + " doesn't exist!");
		}

	}

	@Override
	public void saveImage(int clubId, String type, MultipartFile file) {
		
		String MIMEType = file.getContentType();
		String extension = MIMEType.substring(MIMEType.indexOf("/")+ 1);
		
		
		String fileName = clubId + "." + extension;
		Path finalLocation = Paths.get(rootLocation.toString()+"/club/" + type + "/");
		
		
					
		try(InputStream inputStream = file.getInputStream()){
			Files.copy(inputStream, finalLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);		
		} catch(IOException e) {
		e.printStackTrace(); 
		}
	}

}
