package com.cooksys.secondassessment.twitterapi.dto;

/**
 * 
 * @author Artem Kolin
 *
 *Dto for Tweet output contain SimpleTweetDtos for inReplyTo and repostOf inner Tweet(s) to avoid loops in case of inner 
 *Tweets have their own reply/repost dependencies on other Tweets and those on their own and so on
 */
public class TweetDto {
	
	
	private Integer id;
	private Long posted;
	private UserDto author;  //Spring is smart as heck, mapper convert type to typeDto itself
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
