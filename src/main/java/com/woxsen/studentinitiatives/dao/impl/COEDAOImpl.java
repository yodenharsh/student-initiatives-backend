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

import com.woxsen.studentinitiatives.dao.COEDAO;
import com.woxsen.studentinitiatives.entities.COE;
import com.woxsen.studentinitiatives.entities.User;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.storage.ImageProperties;
import com.woxsen.studentinitiatives.utility.DeleteFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class COEDAOImpl implements COEDAO {

	private EntityManager entityManager;
	private final Path rootLocation;
	private final Path rootLocationClassResource;

	
	@Autowired
	public COEDAOImpl(EntityManager entityManager, ImageProperties properties) {
		this.entityManager = entityManager;
		this.rootLocation = Paths.get(properties.getLocation());
		this.rootLocationClassResource = Paths.get(properties.getLocationClassResource());
	}

	@Override
	public COE findById(int id) {
		Session session = entityManager.unwrap(Session.class);

		COE coe = session.get(COE.class, id);
		if (coe == null)
			throw new EntityNotFoundException("COE with ID = " + id + " was not found");

		return coe;
	}

	@Override
	public List<COE> findAll() {
		Session session = entityManager.unwrap(Session.class);

		List<COE> coes = session.createSelectionQuery("from COE", COE.class).getResultList();
		return coes;
	}

	@Override
	public int add(COE coe, String coFounderEmail) {
		Session session = entityManager.unwrap(Session.class);

		User user = session.get(User.class, coFounderEmail);

		coe.setUser(user);

		session.persist(coe);

		return coe.getId();
	}

	@Override
	public COE patchById(int id, COE coe) {
		Session session = entityManager.unwrap(Session.class);

		COE coeInDB = session.get(COE.class, id);

		if (coeInDB == null)
			throw new EntityNotFoundException("No COE with ID=" + coe.getId() + " was found");

		coeInDB.setMentors(coe.getMentors());
		coeInDB.setMission(coe.getMission());
		coeInDB.setVision(coe.getVision());
		coeInDB.setName(coe.getName());
		coeInDB.setCoFounderName(coe.getCoFounderName());
		
		return coeInDB;
	}

	@Override
	public int deleteById(int coeId) {
		Session session = entityManager.unwrap(Session.class);

		COE coe = session.get(COE.class, coeId);
		if (coe == null)
			throw new EntityNotFoundException("No COE with ID=" + coeId + " was found");
		
		session.remove(coe);
		return coeId;
	}

	@Override
	public InputStreamResource getImage(int coeId, String type) throws NoSuchFileFoundException {
		var imgFile = new ClassPathResource(rootLocationClassResource + "/COE/" + type+"/"+coeId+".jpeg");
		if(!imgFile.exists()) imgFile = new ClassPathResource(rootLocationClassResource + "/COE/" + type+"/"+coeId+".jpg");
		if(!imgFile.exists()) imgFile = new ClassPathResource(rootLocationClassResource + "/COE/" + type+"/"+coeId+".png");
		if(!imgFile.exists()) throw new NoSuchFileFoundException("The given clubId with that type was not found");
		try {
			return new InputStreamResource(imgFile.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			throw new NoSuchFileFoundException(imgFile + " doesn't exist!");
		}
		
	}

	@Override
	public void saveImage(int coeId, String type, MultipartFile file) {
		String MIMEType = file.getContentType();
		String extension = MIMEType.substring(MIMEType.indexOf("/")+ 1);
		
		String[] extensions = {".jpeg",".png",".jpg"};
		
		String fileName = coeId + "." + extension;
		
		DeleteFile.deleteFile(String.valueOf(coeId), rootLocation.toString()+"/COE/"+ type + "/", extensions);
		
		Path finalLocation = Paths.get(rootLocation.toString()+"/COE/" + type + "/");
		
		
		
		try(InputStream inputStream = file.getInputStream()){
			Files.copy(inputStream, finalLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);		
		} catch(IOException e) {
		e.printStackTrace(); 
		}
	}

}
