package com.cooksys.secondassessment.twitterapi.service;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class ValidateService {
	
	private UserService uS;
	private UserRepository uR;

	public ValidateService(UserService uS, UserRepository uR) {
		this.uS = uS;
		this.uR = uR;
	}

	public boolean hasUser(String username) {
		return uS.getThatUser(username)!=null;
	}

	public boolean nameAvailable(String username) {
		Users user = uR.findByUsernameAndDeleted(username,false);
		return user!=null?user.isDeleted():true;
	}

	
}
