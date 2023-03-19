package com.woxsen.studentinitiatives.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="clubs")
public class Club {
	
	
	@Column(name = "club_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clubId;
	
	@Column(name = "club_name")
	private String clubName;
	
	@JsonBackReference(value = "email")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "president_email")
	private User user;
	
	@JsonManagedReference(value = "school")
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinColumn(name = "school_id")
	private School school;
	
	@OneToMany(mappedBy = "eventId", fetch = FetchType.EAGER)
	private List<ClubEvent> events;
	
	@Column(name = "president_name")
	private String presidentName;
	
	@Column(name = "vice_president_name")
	private String vicePresidentName;
	
	@Column(name = "mission")
	private String mission;
	
	@Column(name = "vision")
	private String vision;

	public Club(int clubId, String clubName, User user, School school, String presidentName, String vicePresidentName,
			String mission, String vision, List<ClubEvent> events) {
		this.clubId = clubId;
		this.clubName = clubName;
		this.user = user;
		this.school = school;
		this.events = events;
		this.presidentName = presidentName;
		this.vicePresidentName = vicePresidentName;
		this.mission = mission;
		this.vision = vision;
	}
	
	public Club(String clubName, String presidentName, String vicePresidentName, String mission, String vision) {
		this.clubName = clubName;
		this.presidentName = presidentName;
		this.vicePresidentName = vicePresidentName;
		this.mission = mission;
		this.vision = vision;
	}

	

	public List<ClubEvent> getEvent() {
		return events;
	}

	public void setEvent(List<ClubEvent> events) {
		this.events = events;
	}

	public int getClubId() {
		return clubId;
	}



	public void setClubId(int clubId) {
		this.clubId = clubId;
	}



	public String getClubName() {
		return clubName;
	}



	public void setClubName(String clubName) {
		this.clubName = clubName;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public School getSchool() {
		return school;
	}



	public void setSchool(School school) {
		this.school = school;
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



	public String getVision() {
		return vision;
	}



	public void setVision(String vision) {
		this.vision = vision;
	}



	public Club() {}

	@Override
	public String toString() {
		return "Club [clubId=" + clubId + ", clubName=" + clubName + ", user=" + user + ", school=" + school
				+ ", presidentName=" + presidentName + ", vicePresidentName=" + vicePresidentName + "]";
	}
	
	
}
