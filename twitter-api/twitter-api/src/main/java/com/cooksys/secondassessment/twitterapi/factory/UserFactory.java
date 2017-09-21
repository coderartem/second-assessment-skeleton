package com.cooksys.secondassessment.twitterapi.factory;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.mapper.UserMapper;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;


@Component
public class UserFactory {

	private UserRepository userRepository;
	private TweetRepository tweetRepository;

	public UserFactory(UserRepository userRepository, TweetRepository tweetRepository) {
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
	}
		
	public Users createUser(InputDto input){
		
		if(userRepository.findByCredentialsAndDeleted(input.getCredentials(),true)!=null){
			Users user = reactivateUser(input.getCredentials().getUsernam());
			userRepository.saveAndFlush(user);
			return userRepository.findByCredentialsAndDeleted(input.getCredentials(), false);
		}
		if(userRepository.findByUsername(input.getCredentials().getUsernam())!=null){
			return null;
		}
			Users user = new Users();
			user.setUsername(input.getCredentials().getUsernam());
			user.setProfile(input.getProfile());
			user.setJoined(new Timestamp(System.currentTimeMillis()).getTime());
			user.setCredentials(input.getCredentials());
			user.setDeleted(false);
			userRepository.saveAndFlush(user);
		return user;
	}
	
	@Transactional
	public Users reactivateUser(String usernam) {
		Users user = userRepository.findByUsername(usernam);
		user.setDeleted(false);
		for(Tweet x : tweetRepository.findByAuthorUsernameAndDeleted(user.getUsername(), true)){
			x.setDeleted(false);
		}
		return user;
	}
	
	@Transactional
	public Users patchUser(InputDto input){
		Users user = userRepository.findByCredentialsAndDeleted(input.getCredentials(), false);
		
		if(user!=null){
			
			if(input.getProfile().getFirstName()!=null){
				user.getProfile().setFirstName(input.getProfile().getFirstName());
			}
			if(input.getProfile().getLastName()!=null){
				user.getProfile().setLastName(input.getProfile().getLastName());
			}
			if(input.getProfile().getPhone()!=null){
				user.getProfile().setPhone(input.getProfile().getPhone());
			}
			if(input.getProfile().getEmail()!=null){
				user.getProfile().setEmail(input.getProfile().getEmail());
			}
			return user;
		}
		return null;
	}
	
	
	@Transactional
	public Users deleteUser(String username){
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		if(user==null) return null;
		user.setDeleted(true);
		for(Tweet x : tweetRepository.findByAuthorUsernameAndDeleted(user.getUsername(), false)){
			x.setDeleted(true);
		}
		
		return user;
	}
	
	
}
