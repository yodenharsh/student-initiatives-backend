package com.woxsen.studentinitiatives.dao;

import java.util.List;

import org.springframework.core.io.InputStreamResource;

import com.woxsen.studentinitiatives.entities.COEPosts;

public interface COEPostsDAO {
	
	public int addCOEPost(COEPosts coePost);
	
	public COEPosts findById(int coePostId);
	
	public List<COEPosts> findByCOEId(int coeId);
	
	public List<COEPosts> findAll();
	
	public COEPosts patchCOEPost(COEPosts coePost);
	
	public boolean deleteCOEPostById(int coeId);
	
	public InputStreamResource getImage(int coeId, int coePostId);
	
	public void saveImage(int coeId, int coePostId);
}
