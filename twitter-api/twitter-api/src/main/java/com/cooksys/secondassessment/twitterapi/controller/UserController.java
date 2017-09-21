package com.cooksys.secondassessment.twitterapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.service.UserService;
import com.cooksys.secondassessment.twitterapi.servlet.response.ServletResponse;

@RestController
@RequestMapping("users")
public class UserController {
	
	
	private UserService userSrvice;
	private ServletResponse sResp ;


	public UserController(UserService userService, ServletResponse sResp) {
		this.userSrvice = userService;
		this.sResp = sResp;
	}

	
	@GetMapping
	public List<UserDto> getAllUsers(){
		return userSrvice.getAllUsers();
	}
	
	@GetMapping("/@{username}")
	public UserDto getThatUser(@PathVariable String username, HttpServletResponse response){
		return sResp.userNullCheck(userSrvice.getThatUser(username),response);
	}
	
	
	@PostMapping
	public UserDto addUser(@RequestBody InputDto input, HttpServletResponse response){
		return sResp.userNullCheck(userSrvice.createNewUser(input),response);
	}
	
	@DeleteMapping("@{username}")
	public UserDto deleteThisMF(@PathVariable String username, HttpServletResponse response){
		return sResp.userNullCheck(userSrvice.deleteThisMF(username),response);
	}
	
	@PatchMapping("@{username}")
	public UserDto updateThisMF(@PathVariable String username, @RequestBody InputDto input, HttpServletResponse response){
		return sResp.userNullCheck(userSrvice.updateThisMF(username, input),response);
	}
	
	@PostMapping("@{username}/follow")
	public void followHim(@PathVariable String username, @RequestBody Credentials cred, HttpServletResponse response){
		boolean res = userSrvice.followHim(username, cred);
		response.setStatus(res?HttpServletResponse.SC_OK:HttpServletResponse.SC_NOT_FOUND);
	}
	
	@PostMapping("@{username}/unfollow")
	public void unfollowHim(@PathVariable String username, @RequestBody Credentials cred, HttpServletResponse response){
		boolean res = userSrvice.unfollowHim(username, cred);
		response.setStatus(res?HttpServletResponse.SC_OK:HttpServletResponse.SC_NOT_FOUND);
	}
	
	@GetMapping("@{username}/feed")
	public List<TweetDto> getFeed(@PathVariable String username, HttpServletResponse response){
		return sResp.listOfTweetsNullCheck(userSrvice.getFeed(username), response);
	}
	
	@GetMapping("@{username}/tweets")
	public List<TweetDto> getTweets(@PathVariable String username, HttpServletResponse response){
		return sResp.listOfTweetsNullCheck(userSrvice.getTweets(username), response);
	}
	
	@GetMapping("@{username}/mentions")
	public List<TweetDto> getTweetsWithUserMentioned(@PathVariable String username, HttpServletResponse response){
		return sResp.listOfTweetsNullCheck(userSrvice.whereUserMentioned(username),response);
	}
	
	@GetMapping("@{username}/followers")
	public List<UserDto> myFanClub(@PathVariable String username, HttpServletResponse response){
		return sResp.listOfUsersNullCheck(userSrvice.myFanClub(username), response);
	}
	
	@GetMapping("@{username}/following")
	public List<UserDto> whoAmIFollowing(@PathVariable String username, HttpServletResponse response){
		return sResp.listOfUsersNullCheck(userSrvice.whoAmIFollowing(username), response);
	}
}
