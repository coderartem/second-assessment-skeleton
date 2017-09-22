package com.cooksys.secondassessment.twitterapi.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


/**
 * 
 * @author Artem Kolin
 * 
 * Dto for UsersCreationData input, also Embedded into Users Entity as user Credentials (login and password)
 *
 */
@Embeddable
public class Credentials {
	
	@NotNull
	@Column(name="login")
	private String username;
	@NotNull
	private String password;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
