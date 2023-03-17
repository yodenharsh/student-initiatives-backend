package com.woxsen.studentinitiatives.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.woxsen.studentinitiatives.dao.UserDAO;
import com.woxsen.studentinitiatives.entities.User;
import com.woxsen.studentinitiatives.exceptions.InvalidCredentialsException;

import jakarta.persistence.EntityManager;

@Repository
public class UserDAOImpl implements UserDAO {

	private EntityManager entityManager;
	
	@Autowired
	public UserDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Override
	public void save(User user) {
		Session session = entityManager.unwrap(Session.class);
		
		String password = user.getPassword();
		BasicPasswordEncryptor passwordEnc = new BasicPasswordEncryptor();
		String encPassword = passwordEnc.encryptPassword(password);
		user.setPassword(encPassword);
		session.persist(user);

	}

	@Override
	public void delete(User user) throws InvalidCredentialsException {
		Session session = entityManager.unwrap(Session.class);
		User userInDB = session.get(User.class, user.getEmail());
		if((userInDB!=null) && (userInDB.getPassword() == user.getPassword()))
			session.remove(user);
		else throw new InvalidCredentialsException("Either password or email is wrong");
	}

	@Override
	public Map<String, Integer> loginAndGetID(User user) throws InvalidCredentialsException {
		
		Session session = entityManager.unwrap(Session.class);
		
		User result = session.get(User.class, user.getEmail());
		if(result == null) throw new InvalidCredentialsException("Wrong email");
		
		BasicPasswordEncryptor passwordEnc = new BasicPasswordEncryptor();
		String password = user.getPassword();
		if(passwordEnc.checkPassword(password, result.getPassword())) {
			
			HashMap<String, Integer> map = new HashMap<>();
			if(result.getClub()!= null) map.put("clubId", result.getClub().getClubId());
			else if(result.getCoe()!= null) map.put("coeId", result.getCoe().getId());
			
			return map;
		}
		
		else throw new InvalidCredentialsException("Wrong password");
		
	}

}
