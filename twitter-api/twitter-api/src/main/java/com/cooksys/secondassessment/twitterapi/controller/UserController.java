package com.cooksys.secondassessment.twitterapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	
	private UserService uS;


	public UserController(UserService uS) {
		this.uS = uS;
	}

	
	@GetMapping
	public List<UserDto> getAllUsers(){
		return uS.getAllUsers();
	}
	
	@GetMapping("/@{username}")
	public UserDto getThatUser(@PathVariable String username){
		return uS.getThatUser(username);
	}
	
	
	@PostMapping
	public UserDto addUser(@RequestBody InputDto input){
		return uS.createNewUser(input);
	}
}
