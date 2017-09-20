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
	
	private TweetDto inReplyTo;
	private TweetDto repostOf;
	
	
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
	public TweetDto getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(TweetDto inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public TweetDto getRepostOf() {
		return repostOf;
	}
	public void setRepostOf(TweetDto repostOf) {
		this.repostOf = repostOf;
	}
	

}
