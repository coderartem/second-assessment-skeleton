package com.cooksys.secondassessment.twitterapi.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;

public class TweetDto {
	
	
	private Integer id;
	private UserDto author;  //Spring is smart as heck, mapper convert type to typeDto itself
	private Long posted;
	private String content;
	
	private TweetDto inReplayTo;
	//private Tweet repostOf;
	
	
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
	public TweetDto getInReplayTo() {
		return inReplayTo;
	}
	public void setInReplayTo(TweetDto inReplayTo) {
		this.inReplayTo = inReplayTo;
	}
	

}
