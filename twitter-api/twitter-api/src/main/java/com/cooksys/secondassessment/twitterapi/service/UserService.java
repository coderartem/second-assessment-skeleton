package com.cooksys.secondassessment.twitterapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.twitterapi.converter.ConvertInput;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.mapper.UserMapper;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserMapper uM;

	public UserService(UserRepository userRepository, UserMapper uM) {
		this.userRepository=userRepository;
		this.uM = uM;
	}
	
	
	public UserDto getThatUser(String username){
		return uM.userToUserDto(userRepository.findByUsername(username));
	}

	public UserDto createNewUser(InputDto input) {
		Users user = new ConvertInput(input).getUser();
		userRepository.save(user);
		return getThatUser(user.getUsername());
	}

	public List<UserDto> getAllUsers() {
		return uM.usersToUsersDto(userRepository.findAll());
	}
}
