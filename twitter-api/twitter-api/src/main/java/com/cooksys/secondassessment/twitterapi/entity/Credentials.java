package com.cooksys.secondassessment.twitterapi.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Credentials {
	
	private String usernam;
	private String password;
	
	
	public String getUsernam() {
		return usernam;
	}
	public void setUsernam(String username) {
		this.usernam = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
