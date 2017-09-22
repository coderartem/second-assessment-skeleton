package com.cooksys.secondassessment.twitterapi.factory;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.UsersCreationData;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;


/**
 * 
 * @author Artem Kolin
 * 
 * This component designed to create based on input parameters, delete, reanimate and modify user
 *
 */

@Component
public class UserFactory {

	private UserRepository userRepository;
	private TweetRepository tweetRepository;

	public UserFactory(UserRepository userRepository, TweetRepository tweetRepository) {
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
	}
		
	public Users createUser(UsersCreationData input){
		
		//Checks if user just needs to be reanimated, user exists, input mandatory fields are not null
		
		if(userRepository.findByCredentialsAndDeleted(input.getCredentials(),true)!=null){
			Users user = reactivateUser(input.getCredentials().getUsername());
			userRepository.saveAndFlush(user);
			return userRepository.findByCredentialsAndDeleted(input.getCredentials(), false);
		}
		if(userRepository.findByUsername(input.getCredentials().getUsername())!=null){
			return null;
		}
		if(input.getCredentials().getPassword()==null || input.getCredentials().getUsername()==null || input.getProfile().getEmail()==null){
			return null;
		}
		
		//Creating new user, setting up it's values
		
			Users user = new Users();
			user.setUsername(input.getCredentials().getUsername());
			user.setProfile(input.getProfile());
			user.setJoined(new Timestamp(System.currentTimeMillis()).getTime());
			user.setCredentials(input.getCredentials());
			user.setDeleted(false);
			userRepository.saveAndFlush(user);
			
		return user;
	}
	
	//Reactivation of User with reactivation of all of his tweets
	
	@Transactional
	private Users reactivateUser(String usernam) {
		Users user = userRepository.findByUsername(usernam);
		user.setDeleted(false);
		tweetRepository.findByAuthorUsernameAndDeleted(user.getUsername(), true).forEach(t -> t.setDeleted(false));
		return user;
	}
	
	//User modification
	
	@Transactional
	public Users patchUser(UsersCreationData input){
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
	
	//Deactivation of User with deactivation of all of his tweets
	
	@Transactional
	public Users deleteUser(String username){
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		if(user==null) return null;
		user.setDeleted(true);
		tweetRepository.findByAuthorUsernameAndDeleted(user.getUsername(), false).forEach(t -> t.setDeleted(true));
		
		return user;
	}
	
	
}
