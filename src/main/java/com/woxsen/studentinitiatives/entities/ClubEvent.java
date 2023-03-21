package com.woxsen.studentinitiatives.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "club-events")
public class ClubEvent {

	@Column(name = "event_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int eventId;
	
	@Column(name = "event_title")
	private String title;
	
	@Column(name = "event_desc")
	private String eventDesc;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinColumn(name = "club_id")
	@JsonIgnore
	private Club club;

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public ClubEvent(int eventId, String title, String eventDesc, LocalDateTime date, Club club) {
		this.eventId = eventId;
		this.title = title;
		this.eventDesc = eventDesc;
		this.date = date;
		this.club = club;
	}
	
		
	public ClubEvent(String title, String eventDesc, LocalDateTime date) {
		this.title = title;
		this.eventDesc = eventDesc;
		this.date = date;
	}

	public ClubEvent() {}
	
}
