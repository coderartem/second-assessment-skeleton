package com.cooksys.secondassessment.twitterapi.converter;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;


@Component
public class ConvertInput {

	private UserRepository uR;
	private TweetRepository tR;

	public ConvertInput(UserRepository uR, TweetRepository tR) {
		this.uR = uR;
		this.tR = tR;
		
	}
		
	public Users getUser(InputDto input){
		
		if(uR.findByCredentialsAndDeleted(input.getCredentials(),true)!=null){
			reactivateUser(input.getCredentials().getUsernam());
			return uR.findByCredentials(input.getCredentials());
		}
		if(uR.findByUsername(input.getCredentials().getUsernam())!=null){
			return null;
		}
			Users user = new Users();
			user.setUsername(input.getCredentials().getUsernam());
			user.setProfile(input.getProfile());
			user.setJoined(new Timestamp(System.currentTimeMillis()).getTime());
			user.setCredentials(input.getCredentials());
			user.setDeleted(false);
		return user;
	}
	
	@Transactional
	public void reactivateUser(String usernam) {
		Users user = uR.findByUsername(usernam);
		user.setDeleted(false);
		for(Tweet x : tR.findByAuthorUsernameAndDeleted(user.getUsername(), true)){
			x.setDeleted(false);
		}
		
	}
}
