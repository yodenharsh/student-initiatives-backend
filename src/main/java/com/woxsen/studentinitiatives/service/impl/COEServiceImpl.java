package com.woxsen.studentinitiatives.service.impl;

import java.nio.file.NoSuchFileException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.dao.COEDAO;
import com.woxsen.studentinitiatives.entities.COE;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.service.COEService;

import jakarta.transaction.Transactional;

@Service
public class COEServiceImpl implements COEService {

	private COEDAO coeDAO;
	
	@Autowired
	public COEServiceImpl(COEDAO coeDAO) {
		this.coeDAO = coeDAO;
	}
	
	@Override
	@Transactional
	public COE findById(int id) {
		return coeDAO.findById(id);
	}

	@Override
	@Transactional
	public List<COE> findAll() {
		return coeDAO.findAll();
	}

	@Override
	@Transactional
	public int add(COE coe, String coFounderEmail) {
		return coeDAO.add(coe, coFounderEmail);
	}

	@Override
	@Transactional
	public COE patchById(int id, COE coe) {
		return coeDAO.patchById(id, coe);
	}

	@Override
	@Transactional	
	public int deleteById(int coeId) {
		return coeDAO.deleteById(coeId);
	}

	@Override
	public InputStreamResource getImage(int coeId, String type) throws NoSuchFileFoundException, NoSuchFileException {
		return coeDAO.getImage(coeId, type);
	}

	@Override
	public void saveImage(int coeId, String type, MultipartFile File) {
		coeDAO.saveImage(coeId, type, File);

	}

}
