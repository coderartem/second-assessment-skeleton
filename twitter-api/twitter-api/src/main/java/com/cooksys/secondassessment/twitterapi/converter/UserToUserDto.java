package com.cooksys.secondassessment.twitterapi.converter;

import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Users;

public class UserToUserDto {

	private Users user;
	private UserDto udto = new UserDto();

	public UserToUserDto(Users user) {
		this.user = user;
	}
	
	
	public UserDto convert(){
		udto.setProfile(user.getProfile());
		udto.setUsername(user.getUsername());
		udto.setJoined(user.getJoined());
		return udto;
	}
}
