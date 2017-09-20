package com.cooksys.secondassessment.twitterapi.input.dto;

import com.cooksys.secondassessment.twitterapi.entity.Credentials;

public class TweetInput {
	
	private String content;
	private Credentials credentials;
	
	
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
