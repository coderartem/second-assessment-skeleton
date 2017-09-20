package com.cooksys.secondassessment.twitterapi.service;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.repository.HashTagRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class ValidateService {
	
	private UserService uS;
	private UserRepository uR;
	private HashTagRepository hR;

	public ValidateService(UserService uS, UserRepository uR, HashTagRepository hR) {
		this.uS = uS;
		this.uR = uR;
		this.hR = hR;
	}

	public boolean hasUser(String username) {
		return uR.findByUsername(username)!=null;
	}

	public boolean nameAvailable(String username) {
		Users user = uR.findByUsernameAndDeleted(username,false);
		return user!=null?user.isDeleted():true;
	}

	public boolean tagExists(String tag) {
		return hR.findByLabel(tag)!=null?true:false;
	}

	
}
