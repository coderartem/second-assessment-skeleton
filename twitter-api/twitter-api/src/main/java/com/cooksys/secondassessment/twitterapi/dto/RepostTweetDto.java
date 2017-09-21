package com.cooksys.secondassessment.twitterapi.dto;


public class RepostTweetDto {
	
	private UserDto author;
	private Long posted;
	private TweetDto repostOf;
	
	
	public UserDto getAuthor() {
		return author;
	}
	public Long getPosted() {
		return posted;
	}
	public TweetDto getRepostOf() {
		return repostOf;
	}
	public void setAuthor(UserDto author) {
		this.author = author;
	}
	public void setPosted(Long posted) {
		this.posted = posted;
	}
	public void setRepostOf(TweetDto repostOf) {
		this.repostOf = repostOf;
	}

}
