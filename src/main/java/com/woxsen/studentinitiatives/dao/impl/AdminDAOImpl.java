package com.woxsen.studentinitiatives.dao.impl;

import org.hibernate.Session;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.woxsen.studentinitiatives.dao.AdminDAO;
import com.woxsen.studentinitiatives.entities.Admin;
import com.woxsen.studentinitiatives.entities.User;
import com.woxsen.studentinitiatives.exceptions.InvalidCredentialsException;

import jakarta.persistence.EntityManager;

@Repository
public class AdminDAOImpl implements AdminDAO {

	private EntityManager entityManager;

	@Autowired
	public AdminDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public boolean addAdmin(Admin admin) {
		Session session = entityManager.unwrap(Session.class);
		try {
			String password = admin.getPassword();
			BasicPasswordEncryptor passwordEnc = new BasicPasswordEncryptor();
			String encPassword = passwordEnc.encryptPassword(password);
			admin.setPassword(encPassword);
			session.persist(admin);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeAdmin(Admin admin) throws InvalidCredentialsException  {
		Session session = entityManager.unwrap(Session.class);
		User userInDB = session.get(User.class, admin.getEmail());
		if ((userInDB != null) && (userInDB.getPassword() == admin.getPassword())) {
			session.remove(admin);
			return true;
		} else
			throw new InvalidCredentialsException("Either password or email is wrong");
	}

	@Override
	public boolean login(Admin admin) throws InvalidCredentialsException {
		Session session = entityManager.unwrap(Session.class);
		
		Admin result = session.get(Admin.class, admin.getEmail());
		if(result == null) throw new InvalidCredentialsException("Wrong email");
		
		BasicPasswordEncryptor passwordEnc = new BasicPasswordEncryptor();
		String password = admin.getPassword();
		if(passwordEnc.checkPassword(password, result.getPassword())) {
			return true;
		}
		return false;
	}

}
