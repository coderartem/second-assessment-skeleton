package com.cooksys.secondassessment.twitterapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
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
	
	@DeleteMapping("@{username}")
	public UserDto deleteThisMF(@PathVariable String username){
		return uS.deleteThisMF(username);
	}
	
	@PatchMapping("@{username}")
	public UserDto updateThisMF(@PathVariable String username, @RequestBody InputDto input){
		return uS.updateThisMF(username, input);
	}
	
	@PostMapping("@{username}/follow")
	public void followHim(@PathVariable String username, @RequestBody Credentials cred){
		uS.followHim(username, cred);
	}
	
	@PostMapping("@{username}/unfollow")
	public void unfollowHim(@PathVariable String username, @RequestBody Credentials cred){
		uS.unfollowHim(username, cred);
	}
	
	@GetMapping("@{username}/feed")
	public List<TweetDto> getFeed(@PathVariable String username){
		return uS.getFeed(username);
	}
	
//	@GetMapping("@{username}/tweets")
//	
//	@GetMapping("@{username}/mentions")
//	
//	@GetMapping("@{username}/followers")
	
	@GetMapping("@{username}/following")
	public List<UserDto> whoAmIFollowing(@PathVariable String username){
		return uS.whoAmIFollowing(username);
	}
}
