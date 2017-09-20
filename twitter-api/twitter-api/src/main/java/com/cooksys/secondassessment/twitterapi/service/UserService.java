package com.cooksys.secondassessment.twitterapi.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.converter.ConvertInput;
import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.mapper.UserMapper;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserMapper uM;
	private EntityManager eM;   //Vozmozhno ne nuzhen

	public UserService(UserRepository userRepository, UserMapper uM, EntityManager eM) {
		this.userRepository=userRepository;
		this.uM = uM;
		this.eM = eM;
	}
	
	
	public UserDto getThatUser(String username){
		return uM.userToUserDto(userRepository.findByUsername(username));
	}

	public UserDto createNewUser(InputDto input) {
		Users user = new ConvertInput(input).getUser();  ////Zamenit' na Mapper
		userRepository.save(user);
		return getThatUser(user.getUsername());
	}

	public List<UserDto> getAllUsers() {
		return uM.usersToUsersDto(userRepository.findAll());  ///Adjust to unDeleted only
	}


	@Transactional
	public UserDto deleteThisMF(String username) {
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		if(user==null) return null;
		user.setDeleted(true);
		return uM.userToUserDto(user);
	}

	
	@Transactional
	public UserDto updateThisMF(String username, InputDto input) {
		
		Users user = new ConvertInput(input).getUser();
		Users oldUser = userRepository.findByCredentials(input.getCredentials());
		if(oldUser!=null){
			
			//Some PATCH Code Here
		}
		return null;
	}

	@Transactional
	public int followHim(String username, Credentials cred) {
			Users user = userRepository.findByUsername(username);
			Users follower = userRepository.findByCredentials(cred);
			
			//Add FOLLOWER check code
			
			if(user!=null && follower!=null){
				user.getFollowers().add(follower);
				follower.getFollowing().add(user);
				return 1;
			}else return 0;
	}

	@Transactional
	public int unfollowHim(String username, Credentials cred) {
		Users user = userRepository.findByUsername(username);
		Users follower = userRepository.findByCredentials(cred);
		
		//Add UN-FOLLOWER check code
		
		if(user!=null && follower!=null){
			user.getFollowers().remove(follower);
			follower.getFollowing().remove(user);
			return 1;
		}else return 0;
		
	}


	public List<TweetDto> getFeed(String username) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<UserDto> whoAmIFollowing(String username) {
		return uM.usersToUsersDto(userRepository.findByUsername(username).getFollowing());
	}


	public List<UserDto> myFanClub(String username) {
		return uM.usersToUsersDto(userRepository.findByUsername(username).getFollowers());
	}
	
	
	
}
