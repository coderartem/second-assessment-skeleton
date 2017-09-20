package com.cooksys.secondassessment.twitterapi.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;

public class TweetDto {
	
	
	private Integer id;
	private Users author;
	private Long posted;
	private String content;
	private InReplyToDto inReplayTo;
	private Tweet repostOf;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Users getAuthor() {
		return author;
	}
	public void setAuthor(Users author) {
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
