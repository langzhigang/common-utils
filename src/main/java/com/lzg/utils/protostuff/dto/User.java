package com.lzg.utils.protostuff.dto;

import java.util.Date;

public class User {
	private long id;
	private String name;
	private Date birth;
	private Course course;

	public User() {
		super();
	}

	public User(long id, String name, Date birth, Course course) {
		super();
		this.id = id;
		this.name = name;
		this.birth = birth;
		this.course = course;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
