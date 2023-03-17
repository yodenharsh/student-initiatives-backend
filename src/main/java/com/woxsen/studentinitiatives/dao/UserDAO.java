package com.woxsen.studentinitiatives.dao;

import java.util.Map;

import com.woxsen.studentinitiatives.entities.User;
import com.woxsen.studentinitiatives.exceptions.InvalidCredentialsException;

public interface UserDAO {

	public void save(User user);
	public void delete(User user) throws InvalidCredentialsException;
	public Map<String, Integer> loginAndGetID(User user) throws InvalidCredentialsException;
}
