package com.woxsen.studentinitiatives.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woxsen.studentinitiatives.dao.UserDAO;
import com.woxsen.studentinitiatives.entities.User;
import com.woxsen.studentinitiatives.exceptions.InvalidCredentialsException;
import com.woxsen.studentinitiatives.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private UserDAO userDAO;
	
	@Autowired
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Override
	@Transactional
	public void save(User user) {
		userDAO.save(user);

	}

	@Override
	@Transactional
	public void delete(User user) throws InvalidCredentialsException {
		userDAO.delete(user);
	}

	@Override
	@Transactional
	public Map<String, Integer> loginAndGetID(User user) throws InvalidCredentialsException {
		return userDAO.loginAndGetID(user);
	}

}
