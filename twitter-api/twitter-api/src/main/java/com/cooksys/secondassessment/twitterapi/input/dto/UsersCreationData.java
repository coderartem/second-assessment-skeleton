package com.cooksys.secondassessment.twitterapi.input.dto;

import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Profile;

/**
 * 
 * @author Artem Kolin
 * 
 * Dto for input JSON object that contain users creation data: Profile and Credentials 
 *
 */

public class UsersCreationData {
	
	private Profile profile;
	private Credentials credentials;
	
	
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
