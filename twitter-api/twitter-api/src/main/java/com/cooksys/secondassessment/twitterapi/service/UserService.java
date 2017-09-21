package com.cooksys.secondassessment.twitterapi.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.cfactory.UserFactory;
import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.mapper.TweetMapper;
import com.cooksys.secondassessment.twitterapi.mapper.UserMapper;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class UserService {

	private UserRepository uR;
	private UserMapper uM;  
	private TweetRepository tR;
	private TweetService tS;
	private UserFactory cI;
	private TweetMapper tM;

	public UserService(UserRepository userRepository, UserMapper uM, TweetRepository tR, TweetService tS, UserFactory cI, TweetMapper tM) {
		this.uR=userRepository;
		this.uM = uM;
		this.tR = tR;
		this.tS = tS;
		this.cI = cI;
		this.tM = tM;
	}
	
	
	public UserDto getThatUser(String username){
		return uM.userToUserDto(uR.findByUsernameAndDeleted(username, false));
	}

	public UserDto createNewUser(InputDto input) {
		Users user = cI.getUser(input); 
		if(user!=null){
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

	
	public UserDto updateThisMF(String username, InputDto input) {
		Users user = cI.patchUser(input);
		return uM.userToUserDto(user);
	}

	@Transactional
	public boolean followHim(String username, Credentials cred) {
			Users user = uR.findByUsernameAndDeleted(username, false);
			Users follower = uR.findByCredentialsAndDeleted(cred, false);
			if(user!=null && follower!=null && uR.findByUsernameAndFollowersCredentials(user.getUsername(), follower.getCredentials())==null){
				user.getFollowers().add(follower);
				follower.getFollowing().add(user);
				return true;
			}else return false;
	}

	@Transactional
	public boolean unfollowHim(String username, Credentials cred) {
		Users user = uR.findByUsername(username);
		Users follower = uR.findByCredentials(cred);
		if(user!=null && follower!=null && uR.findByUsernameAndFollowersCredentials(user.getUsername(), follower.getCredentials())!=null){
			user.getFollowers().remove(follower);
			follower.getFollowing().remove(user);
			return true;
		}else return false;
	}


	public List<TweetDto> getFeed(String username) {

		Users user = uR.findByUsernameAndDeleted(username, false);				//Adjust  
		List<TweetDto> res=new ArrayList<>();					//Mogut byt dublikaty tweetov !!!
		if(user!=null){											//Need sorting
			res.addAll(getTweets(username));
			for(Users x : user.getFollowing()){
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
		return tM.tweetsToTweetDtos(tR.findByDeletedAndMentionsMention(false, "@"+username));
	}
	
	
	
}
