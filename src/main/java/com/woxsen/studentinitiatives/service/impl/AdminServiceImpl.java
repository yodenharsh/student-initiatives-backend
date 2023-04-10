package com.woxsen.studentinitiatives.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woxsen.studentinitiatives.dao.AdminDAO;
import com.woxsen.studentinitiatives.entities.Admin;
import com.woxsen.studentinitiatives.exceptions.InvalidCredentialsException;
import com.woxsen.studentinitiatives.service.AdminService;

import jakarta.transaction.Transactional;

@Service
public class AdminServiceImpl implements AdminService {

	private AdminDAO adminDAO;
	
	
	@Autowired
	public AdminServiceImpl(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	@Override
	@Transactional
	public boolean addAdmin(Admin admin) {
		return adminDAO.addAdmin(admin);
	}

	@Override
	@Transactional
	public boolean login(Admin admin) throws InvalidCredentialsException {
		return adminDAO.login(admin);
	}

	@Override
	@Transactional
	public boolean removeAdmin(Admin admin) throws InvalidCredentialsException {
		return adminDAO.removeAdmin(admin);
	}

}
