package com.woxsen.studentinitiatives.dao;

import java.util.List;

import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.School;

public interface SchoolDAO {
	
	public void add(School school);
	
	public void removeById(int schoolId);
	
	public List<Club> getClubListBySchoolId(int schoolId);
	
	public School getById(int schoolId);
	
	public List<School> getSchools();
}
