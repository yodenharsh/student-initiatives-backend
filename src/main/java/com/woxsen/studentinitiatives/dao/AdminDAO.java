package com.woxsen.studentinitiatives.dao;

import com.woxsen.studentinitiatives.entities.Admin;
import com.woxsen.studentinitiatives.exceptions.InvalidCredentialsException;

public interface AdminDAO {
	public boolean addAdmin(Admin admin);
	
	public boolean login(Admin admin) throws InvalidCredentialsException ;
	
	public boolean removeAdmin(Admin admin) throws InvalidCredentialsException;
}
