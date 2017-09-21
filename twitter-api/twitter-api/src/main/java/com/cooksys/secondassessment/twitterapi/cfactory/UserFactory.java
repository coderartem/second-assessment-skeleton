package com.cooksys.secondassessment.twitterapi.cfactory;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;


@Component
public class UserFactory {

	private UserRepository uR;
	private TweetRepository tR;

	public UserFactory(UserRepository uR, TweetRepository tR) {
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
			uR.saveAndFlush(user);
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
	
	@Transactional
	public Users patchUser(InputDto input){
		Users user = uR.findByCredentialsAndDeleted(input.getCredentials(), false);
		
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
	
	
}
