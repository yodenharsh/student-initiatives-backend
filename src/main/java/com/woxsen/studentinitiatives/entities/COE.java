package com.woxsen.studentinitiatives.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Table(name = "COEs")
@Entity
public class COE {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "id")
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cofounder_email")
	private User user;
	
	@Column(name = "name")
	@NotNull
	private String name;
	
	@Column(name = "mentors")
	private String mentors;
	
	@Column(name = "mission")
	private String mission;
	
	@Column(name = "vision")
	private String vision;

	public COE(int id, User user, @NotNull String name, String mentors, String mission, String vision) {
		this.id = id;
		this.user = user;
		this.name = name;
		this.mentors = mentors;
		this.mission = mission;
		this.vision = vision;
	}
	
	
	
	public COE(@NotNull String name, String mentors, String mission, String vision) {
		this.name = name;
		this.mentors = mentors;
		this.mission = mission;
		this.vision = vision;
	}



	public COE() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMentors() {
		return mentors;
	}

	public void setMentors(String mentors) {
		this.mentors = mentors;
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
	
	
}


