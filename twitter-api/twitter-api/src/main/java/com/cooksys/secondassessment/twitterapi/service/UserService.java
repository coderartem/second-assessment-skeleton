package com.cooksys.secondassessment.twitterapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.factory.Sorter;
import com.cooksys.secondassessment.twitterapi.factory.UserFactory;
import com.cooksys.secondassessment.twitterapi.input.dto.UsersCreationData;
import com.cooksys.secondassessment.twitterapi.mapper.TweetMapper;
import com.cooksys.secondassessment.twitterapi.mapper.UserMapper;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

/**
 * 
 * @author Artem Kolin
 * 
 * Names of methods are pretty informative themselves
 * Implementing everywhere null checks on returned from data base information (Users, Tweet(s))
 * Sending List<> results to Sorter injected component to sort them in DESC order based on timestamps (in ASC in case of Mention(s))
 * Using UserMapper to convert Entities to Dtos
 * Using injected UserFactory component to create, modify, delete and reactivate Users
 */
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

	public UserDto createNewUser(UsersCreationData input) {
		return userMapper.userToUserDto(usersFactory.createUser(input));
	}

	public List<UserDto> getAllActiveUsers() {
		List<UserDto> userList = userMapper.usersToUsersDto(userRepository.findByDeleted(false));
		return userList!=null?sort.sortUsers(userList):null;
	}


	@Transactional
	public UserDto deleteThisMF(String username) {
		return userMapper.userToUserDto(usersFactory.deleteUser(username));
	}

	
	public UserDto updateThisMF(String username, UsersCreationData input) {
		Users user = usersFactory.patchUser(input);
		return userMapper.userToUserDto(user);
	}

	/**
	 * Getting user with this username and future follower with these credentials from database and add them to following/follower lists of each other
	 * @param username of User
	 * @param credentials of follower
	 * @return boolean
	 */
	@Transactional
	public boolean followHim(String username, Credentials credentials) {
			Users user = userRepository.findByUsernameAndDeleted(username, false);
			Users follower = userRepository.findByCredentialsAndDeleted(credentials, false);
			if(user!=null && follower!=null && userRepository.findByUsernameAndFollowersCredentials(user.getUsername(), 
					follower.getCredentials())==null && follower!=user){
				user.getFollowers().add(follower);
				follower.getFollowing().add(user);
				return true;
			}else return false;
	}

	/**
	 * 
	 * Getting user with this username and future follower with these credentials from database and remove them to following/follower lists of each other
	 * @param username of User
	 * @param credentials of follower
	 * @return boolean
	 */
	@Transactional
	public boolean unfollowHim(String username, Credentials credentials) {
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		Users follower = userRepository.findByCredentialsAndDeleted(credentials, false);
		if(user!=null && follower!=null && userRepository.findByUsernameAndFollowersCredentials(user.getUsername(), 
				follower.getCredentials())!=null){
			user.getFollowers().remove(follower);
			follower.getFollowing().remove(user);
			return true;
		}else return false;
	}


	/**
	 * Getting user with this username from database, getting all his tweets (using authorTweets() method from TweetService for that) and then getting everybody
	 * this user follows from his following list and do same thing for them. At the end adding all tweets received in a list and send it to Sorter component 
	 * @param username of this user
	 * @return List<TweetDto> - feed of this user
	 */
	public List<TweetDto> getUserFeed(String username) {
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		List<TweetDto> feed=new ArrayList<>();
		if(user==null)return null;											
			feed.addAll(tweetService.authorTweets(username));
			user.getFollowing().forEach(f -> feed.addAll(tweetService.authorTweets(f.getUsername())));
			return sort.sortTweets(feed);
	}
	
	/**
	 *  Getting user with this username from database, getting all his tweets (using authorTweets() method from TweetService for that)
	 * @param username of this user
	 * @return List<TweetDto> - all undeleted tweets of this user
	 */
	public List<TweetDto> getUserTweets(String username){
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		if(user==null)return null; 
			return  tweetService.authorTweets(username);   //There is sorting in authorTweets() already so don't need to implement it here
	}

	/**
	 * Looking for user with this username in database and getting people he following from his following list 
	 * @param username of this user
	 * @return List<UserDto> 
	 */
	public List<UserDto> whoAmIFollowing(String username) {
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		if(user==null)return null;
		List<Users> following = user.getFollowing();
		following.removeIf(t->t.isDeleted()==true);
		List<UserDto> userList = userMapper.usersToUsersDto(following);
		return userList!=null?sort.sortUsers(userList):null;
	}

	/**
	 * Looking for user with this username in database and his followers from his followers list 
	 * @param username of this user
	 * @return List<UserDto> - of followers
	 */
	public List<UserDto> myFanClub(String username) {
		Users user = userRepository.findByUsernameAndDeleted(username, false);
		if(user==null)return null;
		List<Users> followers = user.getFollowers();
		followers.removeIf(t->t.isDeleted()==true);
		List<UserDto> userList = userMapper.usersToUsersDto(followers);
		return userList!=null?sort.sortUsers(userList):null;
	}


	/**
	 * Getting list of tweets where this user was mentioned by matching his username against mentions lists saved in tweets
	 * @param username of this user
	 * @return List<TweetDto> - all tweets this user was mentioned in
	 */
	public List<TweetDto> userMentions(String username) {
		List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweetRepository.findByDeletedAndMentionsMention(false, "@"+username));
		return tweetList!=null?sort.sortTweets(tweetList):null;
	}
	
	
	
}
