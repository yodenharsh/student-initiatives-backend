package com.woxsen.studentinitiatives.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "coe-posts")
@Entity
public class COEPosts {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "desc")
	private String desc;
	
	@Column(name = "date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;
	
	@JoinColumn(name = "coeId")
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST,})
	private COE coeId;

	public COEPosts() {}

	public COEPosts(int id, String title, String desc, LocalDateTime date, COE coeId) {
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.date = date;
		this.coeId = coeId;
	}
	
	

	public COEPosts(String title, String desc, LocalDateTime date) {
		this.title = title;
		this.desc = desc;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public COE getCoeId() {
		return coeId;
	}

	public void setCoeId(COE coeId) {
		this.coeId = coeId;
	}
	
	

	
}
