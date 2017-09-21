package com.cooksys.secondassessment.twitterapi.dto;

import com.cooksys.secondassessment.twitterapi.entity.Users;

public class SimpleTweetDto {
	
	private UserDto author;
	private Long posted;
	private String content;
	
	
	public UserDto getAuthor() {
		return author;
	}
	public Long getPosted() {
		return posted;
	}
	public String getContent() {
		return content;
	}
	public void setAuthor(UserDto author) {
		this.author = author;
	}
	public void setPosted(Long posted) {
		this.posted = posted;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
