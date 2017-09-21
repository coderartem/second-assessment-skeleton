package com.cooksys.secondassessment.twitterapi.dto;

import com.cooksys.secondassessment.twitterapi.entity.Users;

public class ReplyTweetDto {
	
	private UserDto author;
	private Long posted;
	private String content;
	private SimpleTweetDto inReplyTo;
	
	
	public UserDto getAuthor() {
		return author;
	}
	public Long getPosted() {
		return posted;
	}
	public String getContent() {
		return content;
	}
	public SimpleTweetDto getInReplyTo() {
		return inReplyTo;
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
	public void setInReplyTo(SimpleTweetDto inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

}
