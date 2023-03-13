package com.woxsen.studentinitiatives.dao;

import java.nio.file.NoSuchFileException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.entities.COE;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;

public interface COEDAO {
	
	public COE findById(int id);
	
	public List<COE> findAll();
	
	public int add(COE coe, String coFounderEmail);
	
	public COE patchById(int id, COE coe);
	
	public int deleteById(int coeId);
	
	public InputStreamResource getImage(int coeId, String type) throws NoSuchFileException, NoSuchFileFoundException;
	
	public void saveImage(int coeId, String type, MultipartFile File);
	
}
