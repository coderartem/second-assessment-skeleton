package com.cooksys.secondassessment.twitterapi.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.factory.Sorter;
import com.cooksys.secondassessment.twitterapi.factory.UserFactory;
import com.cooksys.secondassessment.twitterapi.input.dto.InputDto;
import com.cooksys.secondassessment.twitterapi.mapper.TweetMapper;
import com.cooksys.secondassessment.twitterapi.mapper.UserMapper;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;  
	private TweetRepository tweetRepository;
	private TweetService tweetService;
	private UserFactory usersFactory;
	private TweetMapper tweetMapper;
	private Sorter sort;

	public UserService(UserRepository userRepository, UserMapper userMapper, TweetRepository tweetRepository, 
			TweetService tweetService, UserFactory usersFactory, TweetMapper tweetMapper, Sorter sort) {
		this.userRepository=userRepository;
		this.userMapper = userMapper;
		this.tweetRepository = tweetRepository;
		this.tweetService = tweetService;
		this.usersFactory = usersFactory;
		this.tweetMapper = tweetMapper;
		this.sort = sort;
	}
	
	
	public UserDto getThatUser(String username){
		return userMapper.userToUserDto(userRepository.findByUsernameAndDeleted(username, false));
	}

	public UserDto createNewUser(InputDto input) {
		return userMapper.userToUserDto(usersFactory.createUser(input));
	}

	public List<UserDto> getAllUsers() {
		List<UserDto> userList = userMapper.usersToUsersDto(userRepository.findByDeleted(false));
		return userList!=null?sort.sortUsers(userList):null;
	}


	@Transactional
	public UserDto deleteThisMF(String username) {
		return userMapper.userToUserDto(usersFactory.deleteUser(username));
	}

	
	public UserDto updateThisMF(String username, InputDto input) {
		Users user = usersFactory.patchUser(input);
		return userMapper.userToUserDto(user);
	}

	@Transactional
	public boolean followHim(String username, Credentials cred) {
			Users user = userRepository.findByUsernameAndDeleted(username, false);
			Users follower = userRepository.findByCredentialsAndDeleted(cred, false);
			if(user!=null && follower!=null && userRepository.findByUsernameAndFollowersCredentials(user.getUsername(), 
					follower.getCredentials())==null && follower!=user){
				user.getFollowers().add(follower);
				follower.getFollowing().add(user);
				return true;
			}else return false;
	}

	@Transactional
	public boolean unfollowHim(String username, Credentials cred) {
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		Users follower = userRepository.findByCredentialsAndDeleted(cred, false);
		if(user!=null && follower!=null && userRepository.findByUsernameAndFollowersCredentials(user.getUsername(), 
				follower.getCredentials())!=null){
			user.getFollowers().remove(follower);
			follower.getFollowing().remove(user);
			return true;
		}else return false;
	}


	public List<TweetDto> getFeed(String username) {

		Users user = userRepository.findByUsernameAndDeleted(username, false);				//Adjust  
		List<TweetDto> res=new ArrayList<>();					//Mogut byt dublikaty tweetov !!!
		if(user!=null){											//Need sorting
			res.addAll(tweetService.authorTweets(username));
			for(Users x : user.getFollowing()){
				res.addAll(tweetService.authorTweets(x.getUsername()));
			}
			return sort.sortTweets(res);
		}
		return null;
	}
	
	public List<TweetDto> getTweets(String username){				//Still need to sort
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		if(user!=null){ 
			return  tweetService.authorTweets(username);
		}
		return null;
	}

	public List<UserDto> whoAmIFollowing(String username) {
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		if(user==null)return null;
		List<Users> following = user.getFollowing();
		following.removeIf(t->t.isDeleted()==true);
		List<UserDto> userList = userMapper.usersToUsersDto(following);
		return userList!=null?sort.sortUsers(userList):null;
	}


	public List<UserDto> myFanClub(String username) {
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		if(user==null)return null;
		List<Users> followers = user.getFollowers();
		followers.removeIf(t->t.isDeleted()==true);
		List<UserDto> userList = userMapper.usersToUsersDto(followers);
		return userList!=null?sort.sortUsers(userList):null;
	}


	public List<TweetDto> whereUserMentioned(String username) {
		List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweetRepository.findByDeletedAndMentionsMention(false, "@"+username));
		return tweetList!=null?sort.sortTweets(tweetList):null;
	}
	
	
	
}
