package com.cooksys.secondassessment.twitterapi.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Credentials {
	
	@NotNull
	private String usernam;
	@NotNull
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
