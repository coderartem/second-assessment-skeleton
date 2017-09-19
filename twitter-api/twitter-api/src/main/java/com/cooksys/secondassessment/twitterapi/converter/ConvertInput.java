package com.cooksys.secondassessment.twitterapi.converter;

import java.sql.Timestamp;

import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;

public class ConvertInput {

	private InputDto input;
	private Users user = new Users();
	
	public ConvertInput(InputDto input) {
		this.input = input;
	}
	
	public Users getUser(){
			user.setUsername(input.getCredentials().getUsername());
			user.setProfile(input.getProfile());
			user.setJoined(new Timestamp(0).getTime());
			user.setCredentials(input.getCredentials());
		return user;
	}
}
