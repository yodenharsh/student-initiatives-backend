package com.woxsen.studentinitiatives.service.impl;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.studentinitiatives.dao.ClubDAO;
import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.misc.ClubAdd;
import com.woxsen.studentinitiatives.exceptions.NoSuchFileFoundException;
import com.woxsen.studentinitiatives.service.ClubService;

import jakarta.transaction.Transactional;

@Service
public class ClubServiceImpl implements ClubService {

	private ClubDAO clubDAO;
	
	public ClubServiceImpl(ClubDAO clubDAO) {
		this.clubDAO = clubDAO;
	}
	
	@Override
	@Transactional
	public Club findById(int clubId) {
		return clubDAO.findById(clubId);
	}

	@Override
	@Transactional
	public List<Club> findAll() {
		return clubDAO.findAll();
	}

	@Override
	public InputStreamResource getImage(int clubId, String type) throws NoSuchFileFoundException {
		return clubDAO.getImage(clubId, type);
	}

	@Override
	public void saveImage(int clubId, String type, MultipartFile file) {
		clubDAO.saveImage(clubId, type, file);
	}

	@Override
	@Transactional
	public void deleteById(int clubId) {
		clubDAO.deleteById(clubId);
	}


	@Override
	@Transactional
	public int add(ClubAdd club) {
		return clubDAO.add(club);
		
	}

}
