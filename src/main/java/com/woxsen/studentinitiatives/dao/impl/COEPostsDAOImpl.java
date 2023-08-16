package com.woxsen.studentinitiatives.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.dao.COEPostsDAO;
import com.woxsen.studentinitiatives.entities.COE;
import com.woxsen.studentinitiatives.entities.COEPosts;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.storage.ImageProperties;
import com.woxsen.studentinitiatives.utility.DeleteFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class COEPostsDAOImpl implements COEPostsDAO {

	private EntityManager entityManager;
	private final Path rootLocation;
	private final Path rootLocationClassResource;

	@Autowired
	public COEPostsDAOImpl(EntityManager entityManager, ImageProperties properties) {
		this.entityManager = entityManager;
		this.rootLocation = Paths.get(properties.getLocation());
		this.rootLocationClassResource = Paths.get(properties.getLocationClassResource());
	}

	@Override
	public int addCOEPost(COEPosts coePost) {

		Session session = entityManager.unwrap(Session.class);

		session.persist(coePost);

		return coePost.getId();

	}

	@Override
	public COEPosts findById(int coePostId) {
		Session session = entityManager.unwrap(Session.class);

		COEPosts coePost = session.get(COEPosts.class, coePostId);

		if (coePost == null)
			throw new EntityNotFoundException("No post with id=" + coePostId + " was found");

		return coePost;
	}

	@Override
	public List<COEPosts> findByCOEId(int coeId) {
		Session session = entityManager.unwrap(Session.class);

		COE coe = session.get(COE.class, coeId);
		if (coe == null)
			throw new EntityNotFoundException("COE with coeId = " + coeId + " was not found");

		SelectionQuery<COEPosts> q = session.createSelectionQuery("from COEPosts c where c.coeId=:p", COEPosts.class);

		q.setParameter("p", coe);

		List<COEPosts> coePosts = q.getResultList();

		return coePosts;

	}
	
	@Override
	public List<COEPosts> findByDateRange(LocalDateTime start, LocalDateTime end){
		Session session = entityManager.unwrap(Session.class);
		
		SelectionQuery<COEPosts> q = session.createSelectionQuery("from COEPosts c where c.date >= :s AND c.date <= :e", COEPosts.class);
		
		q.setParameter("s", start);
		q.setParameter("e", end);
		
		List<COEPosts> coePosts = q.getResultList();
		
		return coePosts;
	}

	@Override
	public List<COEPosts> findAll() {
		Session session = entityManager.unwrap(Session.class);

		List<COEPosts> coePosts = session.createSelectionQuery("from COEPosts", COEPosts.class).getResultList();

		return coePosts;
	}

	@Override
	public COEPosts patchCOEPost(COEPosts coePost) {
		Session session = entityManager.unwrap(Session.class);

		COEPosts coePostFromDB = session.get(COEPosts.class, coePost.getId());
		if (coePostFromDB == null)
			throw new EntityNotFoundException("No COEPost with id = " + coePost.getId() + " was found");

		coePostFromDB.setDate(coePost.getDate());
		coePostFromDB.setDesc(coePost.getDesc());
		coePostFromDB.setTitle(coePost.getTitle());

		return coePostFromDB;
	}

	@Override
	public boolean deleteCOEPostById(int coePostId) {
		Session session = entityManager.unwrap(Session.class);

		COEPosts coePost = session.get(COEPosts.class, coePostId);
		if (coePost == null)
			throw new EntityNotFoundException("COE Post with id = " + coePostId + " was not found in the database");
		int coeId = coePost.getCoeId().getId();

		try {
			session.remove(coePost);
			DeleteFile.deleteFile(String.valueOf(coeId), rootLocation.toString() + "/COE/posts/" + coeId + "/",
					new String[] { ".jpeg", ".png", ".jpg" });
			DeleteFile.deleteFile(String.valueOf(coeId), rootLocation.toString() + "/COE/posts/" + coeId + "/",
					new String[] { ".pdf", ".zip" });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public InputStreamResource getImage(int coeId, int coePostId) throws NoSuchFileFoundException {
		var imgFile = new ClassPathResource(
				rootLocationClassResource + "/COE/posts/" + coeId + "/" + coePostId + ".jpeg");
		if (!imgFile.exists())
			imgFile = new ClassPathResource(
					rootLocationClassResource + "/COE/posts/" + coeId + "/" + coePostId + ".jpg");
		if (!imgFile.exists())
			imgFile = new ClassPathResource(
					rootLocationClassResource + "/COE/posts/" + coeId + "/" + coePostId + ".png");
		if (!imgFile.exists())
			throw new NoSuchFileFoundException(
					"Image with coeId=" + coeId + " and coePostId=" + coePostId + " was not found");
		try {
			return new InputStreamResource(imgFile.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			throw new NoSuchFileFoundException(imgFile + " doesn't exist!");
		}
	}

	@Override
	public void saveImage(MultipartFile file, int coeId, int coePostId) {
		String MIMEType = file.getContentType();
		String extension = MIMEType.substring(MIMEType.indexOf("/") + 1);

		String[] extensions = { ".jpeg", ".png", ".jpg" };

		String fileName = coePostId + "." + extension;

		DeleteFile.deleteFile(String.valueOf(coePostId), rootLocation.toString() + "/COE/posts/" + coeId + "/",
				extensions);

		Path finalLocation = Paths.get(rootLocation.toString() + "/COE/posts/" + coeId + "/");
		if (!Files.exists(finalLocation))
			try {
				Files.createDirectories(finalLocation);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, finalLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public InputStreamResource getFile(int coePostId, int coeId) throws NoSuchFileFoundException {
		String extension = "a.zip";
		var file = new ClassPathResource(rootLocationClassResource + "/COE/posts/" + coeId + "/" + coePostId + ".zip");
		if (!file.exists()) {
			file = new ClassPathResource(rootLocationClassResource + "/COE/posts/" + coeId + "/" + coePostId + ".pdf");
			extension = "a.pdf";
		}
		if (!file.exists())
			throw new NoSuchFileFoundException(
					"File with coeId=" + coeId + " and coePostId=" + coePostId + " was not found");
		try {
			InputStreamResource returnThisFile = new InputStreamResource(file.getInputStream(), extension);
			return returnThisFile;
		} catch (IOException e) {
			e.printStackTrace();
			throw new NoSuchFileFoundException(file + " doesn't exist!");
		}
	}

	@Override
	public void saveFile(MultipartFile file, int coeId, int coePostId) {

		String MIMEType = file.getContentType();
		String extension = MIMEType.substring(MIMEType.indexOf("/") + 1);

		String[] extensions = { ".pdf", ".zip" };

		String fileName = coePostId + "." + extension;

		DeleteFile.deleteFile(String.valueOf(coePostId), rootLocation.toString() + "/COE/posts/" + coeId + "/",
				extensions);

		Path finalLocation = Paths.get(rootLocation.toString() + "/COE/posts/" + coeId + "/");
		if (!Files.exists(finalLocation))
			try {
				Files.createDirectories(finalLocation);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, finalLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
