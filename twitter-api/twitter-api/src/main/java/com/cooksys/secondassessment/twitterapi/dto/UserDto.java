package com.cooksys.secondassessment.twitterapi.dto;

import com.cooksys.secondassessment.twitterapi.entity.Profile;

public class UserDto {
	
	private String username;
	private Profile profile;
	private Long joined;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Long getJoined() {
		return joined;
	}
	public void setJoined(Long joined) {
		this.joined = joined;
	}

}
