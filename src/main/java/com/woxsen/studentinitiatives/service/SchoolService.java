package com.woxsen.studentinitiatives.service;

import java.util.List;

import com.woxsen.studentinitiatives.entities.Club;
import com.woxsen.studentinitiatives.entities.School;

public interface SchoolService {
	public void add(School school);
	
	public void removeById(int schoolId);
	
	public List<Club> getClubListBySchoolId(int schoolId);
	
	public School getById(int schoolId);
	
	public List<School> getSchools();
}
