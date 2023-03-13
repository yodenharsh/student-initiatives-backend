package com.woxsen.studentinitiatives.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woxsen.studentinitiatives.dao.SchoolDAO;
import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.School;
import com.woxsen.studentinitiatives.service.SchoolService;

import jakarta.transaction.Transactional;

@Service
public class SchoolServiceImpl implements SchoolService {

	private SchoolDAO schoolDAO;
	
	@Autowired
	public SchoolServiceImpl(SchoolDAO schoolDAO) {
		this.schoolDAO = schoolDAO;
	}
	
	@Override
	@Transactional
	public void add(School school) {
		schoolDAO.add(school);
	}

	@Override
	@Transactional
	public void removeById(int schoolId) {
		schoolDAO.removeById(schoolId);
	}

	@Override
	@Transactional
	public List<Club> getClubListBySchoolId(int schoolId) {
		return schoolDAO.getClubListBySchoolId(schoolId);
	}

	@Override
	public School getById(int schoolId) {
		return schoolDAO.getById(schoolId);
	}

	@Override
	public List<School> getSchools() {
		return schoolDAO.getSchools();
	}

}
