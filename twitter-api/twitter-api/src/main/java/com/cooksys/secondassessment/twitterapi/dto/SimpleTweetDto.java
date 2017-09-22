package com.cooksys.secondassessment.twitterapi.dto;


/**
 * 
 * @author Artem Kolin
 * 
 * This Dto designed to be used inside of repost and reply Tweets to avoid inner loops if inner Tweets have their own inner repost to or reply to Tweets and so on  
 *
 */

public class SimpleTweetDto {
	
	private Integer id;
	private Long posted;
	private UserDto author;
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
