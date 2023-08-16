package com.woxsen.studentinitiatives.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.entities.COEPosts;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;

public interface COEPostsDAO {
	
	public int addCOEPost(COEPosts coePost);
	
	public COEPosts findById(int coePostId);
	
	public List<COEPosts> findByCOEId(int coeId);
	
	public List<COEPosts> findAll();
	
	public List<COEPosts> findByDateRange(LocalDateTime start, LocalDateTime end);
	
	public COEPosts patchCOEPost(COEPosts coePost);
	
	public boolean deleteCOEPostById(int coeId);
	
	public InputStreamResource getImage(int coeId, int coePostId) throws NoSuchFileFoundException;
	
	public void saveImage(MultipartFile file, int coeId, int coePostId);
	
	public InputStreamResource getFile(int coeId, int coePostId) throws NoSuchFileFoundException;
	
	public void saveFile(MultipartFile file, int coeId, int coePostId);
}
