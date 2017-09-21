package com.cooksys.secondassessment.twitterapi.service;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.repository.HashTagRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class ValidateService {
	
	private UserRepository userRepository;
	private HashTagRepository hashTagRepository;

	public ValidateService(UserRepository userRepository, HashTagRepository hashTagRepository) {
		this.userRepository = userRepository;
		this.hashTagRepository = hashTagRepository;
	}

	public boolean userExists(String username) {
		return userRepository.findByUsernameAndDeleted(username,false)!=null;
	}

	public boolean nameAvailable(String username) {
		return userRepository.findByUsername(username)==null;
	}

	public boolean tagExists(String label) {
		return hashTagRepository.findByLabel(label)!=null;
	}

	
}
