package com.cooksys.secondassessment.twitterapi.dto;

public class TweetDto {
	
	
	private Integer id;
	private UserDto author;  //Spring is smart as heck, mapper convert type to typeDto itself
	private Long posted;
	private String content;
	
	private SimpleTweetDto inReplyTo;
	private SimpleTweetDto repostOf;
	
	
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
	public SimpleTweetDto getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(SimpleTweetDto inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public SimpleTweetDto getRepostOf() {
		return repostOf;
	}
	public void setRepostOf(SimpleTweetDto repostOf) {
		this.repostOf = repostOf;
	}
	

}
