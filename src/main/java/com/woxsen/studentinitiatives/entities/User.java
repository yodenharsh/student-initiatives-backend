package com.woxsen.studentinitiatives.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
	
	@Column(name = "email")
	@Id
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String email;
	
	@Column(name = "password")
	@JsonIgnore
	private String password;
	
	@JsonManagedReference(value = "email")
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private Club club;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Club getClub() {
		return club;
	}
	public void setClub(Club club) {
		this.club = club;
	}
	
	public User() {}
	public User(String email, String password, Club club) {
		super();
		this.email = email;
		this.password = password;
		this.club = club;
	}
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
