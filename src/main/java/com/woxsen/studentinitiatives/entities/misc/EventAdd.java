package com.woxsen.studentinitiatives.entities.misc;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EventAdd {

	private String title;

	private String eventDesc;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;

	private int clubId;
	
	public EventAdd(String title, String eventDesc, LocalDateTime date, int clubId) {
		this.title = title;
		this.eventDesc = eventDesc;
		this.date = date;
		this.clubId = clubId;
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
	
	public int getClubId() {
		return clubId;
	}

	public void setClubId(int clubId) {
		this.clubId = clubId;
	}

	public EventAdd() {}
}
