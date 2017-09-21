package com.cooksys.secondassessment.twitterapi.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.converter.ConvertInput;
import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.mapper.UserMapper;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class UserService {

	private UserRepository uR;
	private UserMapper uM;
	private EntityManager eM;   //Vozmozhno ne nuzhen
	private TweetRepository tR;
	private TweetService tS;
	private ConvertInput cI;

	public UserService(UserRepository userRepository, UserMapper uM, EntityManager eM, TweetRepository tR, TweetService tS, ConvertInput cI) {
		this.uR=userRepository;
		this.uM = uM;
		this.eM = eM;
		this.tR = tR;
		this.tS = tS;
		this.cI = cI;
	}
	
	
	public UserDto getThatUser(String username){
		return uM.userToUserDto(uR.findByUsernameAndDeleted(username, false));
	}

	public UserDto createNewUser(InputDto input) {
		Users user = cI.getUser(input);  ////Zamenit' na Mapper
		if(user!=null){
			uR.saveAndFlush(user);
		return getThatUser(user.getUsername());
		}return null;
	}

	public List<UserDto> getAllUsers() {
		return uM.usersToUsersDto(uR.findByDeleted(false));  ///Adjust to unDeleted only
	}


	@Transactional
	public UserDto deleteThisMF(String username) {
		Users user = uR.findByUsernameAndDeleted(username, false);
		if(user==null) return null;
		user.setDeleted(true);
		for(Tweet x : tR.findByAuthorUsernameAndDeleted(user.getUsername(), false)){
			x.setDeleted(true);
		}
		return uM.userToUserDto(user);
	}

	
	@Transactional
	public UserDto updateThisMF(String username, InputDto input) {
		
		Users user = cI.getUser(input);
		Users oldUser = uR.findByCredentials(input.getCredentials());
		if(oldUser!=null){
			
			//Some PATCH Code Here
		}
		return null;
	}

	@Transactional
	public int followHim(String username, Credentials cred) {
			Users user = uR.findByUsernameAndDeleted(username, false);
			Users follower = uR.findByCredentialsAndDeleted(cred, false);
			if(user!=null && follower!=null && uR.findByUsernameAndFollowersCredentials(user.getUsername(), follower.getCredentials())==null){
				user.getFollowers().add(follower);
				follower.getFollowing().add(user);
				return 1;
			}else return 0;
	}

	@Transactional
	public int unfollowHim(String username, Credentials cred) {
		Users user = uR.findByUsername(username);
		Users follower = uR.findByCredentials(cred);
		if(user!=null && follower!=null && uR.findByUsernameAndFollowersCredentials(user.getUsername(), follower.getCredentials())!=null){
			user.getFollowers().remove(follower);
			follower.getFollowing().remove(user);
			return 1;
		}else return 0;
	}


	public List<TweetDto> getFeed(String username) {

		Users user = uR.findByUsername(username);
		List<TweetDto> res=new ArrayList<>();					//Mogut byt dublikaty tweetov !!!
		if(user!=null && !user.isDeleted()){					//Need sorting
			res.addAll(getTweets(username));						
			List<Users> following = uR.findByUsernameAndFollowingDeleted(username, false);
			for(Users x : following){
				res.addAll(tS.authorTweets(x.getUsername()));
			}
			return res;
		}
		return null;
	}
	
	public List<TweetDto> getTweets(String username){				//Still need to sort
		Users user = uR.findByUsername(username);
		if(user!=null && !user.isDeleted()){ 
			
			return  tS.authorTweets(username);
		}
		return new ArrayList<TweetDto>();
		
	}


	public List<UserDto> whoAmIFollowing(String username) {
		List<Users> following = uR.findByUsernameAndDeleted(username, false).getFollowing();
		following.removeIf(t->t.isDeleted()==true);
		return uM.usersToUsersDto(following);
	}


	public List<UserDto> myFanClub(String username) {
		List<Users> followers = uR.findByUsernameAndDeleted(username, false).getFollowers();
		followers.removeIf(t->t.isDeleted()==true);
		return uM.usersToUsersDto(followers);
	}


	public List<TweetDto> whereUserMentioned(String username) {
		tR.findByMentionsIn(username);
		return null;
	}
	
	
	
}
