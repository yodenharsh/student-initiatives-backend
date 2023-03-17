package com.woxsen.studentinitiatives.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.dao.COEDAO;
import com.woxsen.studentinitiatives.dao.COEPostsDAO;
import com.woxsen.studentinitiatives.entities.COE;
import com.woxsen.studentinitiatives.entities.COEPosts;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.service.COEPostsService;

import jakarta.transaction.Transactional;

@Service
public class COEPostsServiceImpl implements COEPostsService {

	private COEPostsDAO coePostsDAO;
	private COEDAO coeDAO;
	
	
	@Autowired
	public COEPostsServiceImpl(COEPostsDAO coePostsDAO, COEDAO coeDAO) {
		this.coePostsDAO = coePostsDAO;
		this.coeDAO = coeDAO;
	}

	@Override
	@Transactional
	public int addCOEPost(COEPosts coePost, int coeId) {
		COE coe = coeDAO.findById(coeId);
		coePost.setCoeId(coe);
		return coePostsDAO.addCOEPost(coePost);
	}

	@Override
	@Transactional
	public COEPosts findById(int coePostId) {
		return coePostsDAO.findById(coePostId);
	}

	@Override
	@Transactional
	public List<COEPosts> findByCOEId(int coeId) {
		return coePostsDAO.findByCOEId(coeId);
	}

	@Override
	@Transactional
	public List<COEPosts> findAll() {
		return coePostsDAO.findAll();
	}

	@Override
	@Transactional
	public COEPosts patchCOEPost(COEPosts coePost) {
		return coePostsDAO.patchCOEPost(coePost);
	}

	@Override
	@Transactional
	public boolean deleteCOEPostById(int coeId) {
		return coePostsDAO.deleteCOEPostById(coeId);
	}

	@Override
	public InputStreamResource getImage(int coeId, int coePostId) throws NoSuchFileFoundException {
		return coePostsDAO.getImage(coeId, coePostId);
	}

	@Override
	public void saveImage(MultipartFile file, int coeId, int coePostId) {
		coePostsDAO.saveImage(file, coeId, coePostId);
	}

	@Override
	public InputStreamResource getFile(int coeId, int coePostId) throws NoSuchFileFoundException {
		return coePostsDAO.getFile(coeId, coePostId);
	}

	@Override
	public void saveFile(MultipartFile file, int coeId, int coePostId) {
		coePostsDAO.saveFile(file, coeId, coePostId);
	}

}
