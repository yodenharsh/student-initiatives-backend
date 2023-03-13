package com.woxsen.studentinitiatives.entities.misc;


public class ClubAdd {
	
	private String clubName;

	private String presidentName;

	private String vicePresidentName;

	private String mission;

	private String vision;

	private int schoolId;

	private String email;

	public ClubAdd(String clubName, String presidentName, String vicePresidentName, String mission, String vision,
			int schoolId, String email) {
		this.clubName = clubName;
		this.presidentName = presidentName;
		this.vicePresidentName = vicePresidentName;
		this.mission = mission;
		this.vision = vision;
		this.schoolId = schoolId;
		this.email = email;
	}
	
	public ClubAdd() {}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getPresidentName() {
		return presidentName;
	}

	public void setPresidentName(String presidentName) {
		this.presidentName = presidentName;
	}

	public String getVicePresidentName() {
		return vicePresidentName;
	}

	public void setVicePresidentName(String vicePresidentName) {
		this.vicePresidentName = vicePresidentName;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVision() {
		return vision;
	}
	
	public void setVision(String vision) {
		this.vision = vision;
	}
	
}
