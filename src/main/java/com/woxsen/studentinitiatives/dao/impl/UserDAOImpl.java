package com.woxsen.studentinitiatives.dao.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.woxsen.studentinitiatives.dao.UserDAO;
import com.woxsen.studentinitiatives.entities.Club;
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
	public Club loginAndGetClubID(User user) throws InvalidCredentialsException {
		
		Session session = entityManager.unwrap(Session.class);
		
		User result = session.get(User.class, user.getEmail());
		if(result == null) throw new InvalidCredentialsException("Wrong email");
		
		if(result.getPassword().equals(user.getPassword())) return result.getClub();
		else throw new InvalidCredentialsException("Wrong password");
		
	}

}
