package com.cooksys.secondassessment.twitterapi.factory;

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
			reactivateUser(input.getCredentials().getUsername());
			System.out.println(input.getCredentials().getUsername());
			System.out.println(uR.findByCredentialsAndDeleted(input.getCredentials(), false)!=null);
			return uR.findByCredentialsAndDeleted(input.getCredentials(), false);  //pomenyal na AndDeleted 9.21 10.21am
		}
		if(uR.findByUsername(input.getCredentials().getUsername())!=null){
			return null;
		}
			Users user = new Users();
			user.setUsername(input.getCredentials().getUsername());
			user.setProfile(input.getProfile());
			user.setJoined(new Timestamp(System.currentTimeMillis()).getTime());
			user.setCredentials(input.getCredentials());
			user.setDeleted(false);
			uR.saveAndFlush(user);
		return user;
	}
	
	@Transactional
	public void reactivateUser(String usernam) {
		System.out.println("Lol");
		Users user = uR.findByUsername(usernam);
		user.setDeleted(false);
		for(Tweet x : tR.findByAuthorUsernameAndDeleted(user.getUsername(), true)){
			x.setDeleted(false);
		}
		System.out.println("finish");
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
