package com.cooksys.secondassessment.twitterapi.dto;

import javax.persistence.Embeddable;


public class InReplyToDto {
	
	private Integer id;
	private UserDto author;
	private Long posted;
	private String content;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UserDto getAuthor() {
		return author;
	}
	public void setAuthor(UserDto author) {
		this.author = author;
	}
	public Long getPosted() {
		return posted;
	}
	public void setPosted(Long posted) {
		this.posted = posted;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
