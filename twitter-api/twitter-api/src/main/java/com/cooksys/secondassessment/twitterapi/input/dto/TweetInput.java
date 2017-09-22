package com.cooksys.secondassessment.twitterapi.input.dto;

import com.cooksys.secondassessment.twitterapi.entity.Credentials;

/**
 * 
 * @author Artem Kolin
 * 
 * Dto for Tweet input JSON object
 *
 */

public class TweetInput {
	
	private String content;
	private Credentials credentials;
	
	public TweetInput() {
		// TODO Auto-generated constructor stub
	}
	
	public TweetInput(String content,  Credentials credentials) {
		this.content = content;
		this.credentials = credentials;
	}
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
