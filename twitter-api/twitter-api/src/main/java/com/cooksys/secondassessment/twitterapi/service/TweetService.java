package com.cooksys.secondassessment.twitterapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Context;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Hashtag;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.factory.ContextFactory;
import com.cooksys.secondassessment.twitterapi.factory.Sorter;
import com.cooksys.secondassessment.twitterapi.factory.TweetFactory;
import com.cooksys.secondassessment.twitterapi.input.dto.TweetInput;
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
 * Using TweetMapper to convert Entities to Dtos 
 * Using injected TweetFactory to create and delete tweets
 */
@Service
public class TweetService {
	
	private TweetMapper tweetMapper;
	private UserRepository userRepository;
	private TweetRepository tweetRepository;
	private UserMapper userMapper;
	private TweetFactory tweetFactory;
	private ContextFactory contextFactory;
	private Sorter sort;

	public TweetService(TweetMapper tweetMapper,  UserRepository userRepository, TweetRepository tweetRepository, UserMapper userMapper, 
			TweetFactory tweetFactory, ContextFactory contextFactory, Sorter sort) {
		this.tweetMapper = tweetMapper;
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
		this.userMapper = userMapper;
		this.tweetFactory = tweetFactory;
		this.contextFactory = contextFactory;
		this.sort = sort;
	}


	public List<TweetDto> getAll() {		
		List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweetRepository.findByDeleted(false));
		return tweetList!=null?sort.sortTweets(tweetList):null;
	}

	public TweetDto postNewTweet(TweetInput tweetInput) {
		if(userRepository.findByCredentialsAndDeleted(tweetInput.getCredentials(), false)==null)return null;
		return tweetMapper.tweetToTweetDto(tweetFactory.createNewTweet(tweetInput));
	}

	public TweetDto getThatTweet(Integer id) {		
		return tweetMapper.tweetToTweetDto(tweetRepository.findByIdAndDeletedAndAuthorDeleted(id,false,false));
	}

	@Transactional
	public TweetDto deleteThisCrap(Integer id, Credentials cred) {
			return tweetMapper.tweetToTweetDto(tweetFactory.deleteTweet(id, cred));
	}

	/**
	 * Adding liker to LikedBy list in Tweet Entity and this tweet in Liked list in Users Entity 
	 * @param id - tweet id
	 * @param credentials - liker credentials
	 * @return boolean
	 */
	@Transactional
	public boolean like(Integer id, Credentials credentials) {	
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		Users user = userRepository.findByCredentialsAndDeleted(credentials, false);
		if(tweet==null || user==null)return false;
				tweet.getLikedBy().add(user);
				user.getLiked().add(tweet);
			return true;
	}

	/**
	 * Creating using TweetFactory injected component and assign Tweet with this id asinReplyTo property of Tweet Entity
	 * @param id - tweet id
	 * @param tweetInput - Tweet input object based on accepted JSON object
	 * @return TweetDto - Tweet in reply to Tweet with this id
	 */
	@Transactional
	public TweetDto reply(Integer id, TweetInput tweetInput) {
		Tweet inReplyTo = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id,false,false);
		if(inReplyTo==null || userRepository.findByCredentialsAndDeleted(tweetInput.getCredentials(), false)==null)return null;
		Tweet tweet = tweetFactory.createNewTweet(tweetInput);
				inReplyTo.getReplies().add(tweet);
				tweet.setInReplyTo(inReplyTo);
			return tweetMapper.tweetToTweetDto(tweet);
	}
	/**
	 * Looking in database for user with these credentials and Tweet with this id, creating new Tweet using TweetFactory injected component
	 * @param id - tweet id
	 * @param credentials - reposter credentials
	 * @return TweetDto - repost Tweet of Tweet with this id
	 */
	@Transactional
	public TweetDto repost(Integer id, Credentials credentials) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		if(tweet==null || userRepository.findByCredentialsAndDeleted(credentials, false)==null) return null;
				Tweet repost = tweetFactory.createRepostTweet(credentials);
				repost.setRepostOf(tweet);
			return tweetMapper.tweetToTweetDto(repost);
	}
	/**
	 * Looking in database for Tweet with this id and for users that have that id in their Liked list
	 * @param id - Tweet id
	 * @return  List<UserDto> - list of users who liked this Tweet
	 */
	public List<UserDto> getTweetLikers(Integer id) {
		if(tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false)==null)return null;
			List<UserDto> userList = userMapper.usersToUsersDto(userRepository.findByDeletedAndLikedId(false,id));
			return userList!=null?sort.sortUsers(userList):null;
	}
	/**
	 * Looking for Tweet with this id in database and return it's list of replier's (property of Entity)
	 * @param id - Tweet id
	 * @return List<TweetDto> - list of Tweet(s) that replies on this tweet
	 */
	public List<TweetDto> getReplies(Integer id) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		if(tweet==null)return null;
			List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweet.getReplies());
			return tweetList!=null?sort.sortTweets(tweetList):null;
	}
	
	/**
	 * Looking in database for Tweet(s) thet contain tweet with this id as repostOf value 
	 * @param id - id of tweet
	 * @return List<TweetDto> - reposts of tweet with this id
	 */
	public List<TweetDto> getRepsots(Integer id) {	
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		if(tweet==null)return null;
			List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweetRepository.findByRepostOfAndDeleted(tweet, false));
			return tweetList!=null?sort.sortTweets(tweetList):null;
	}
	
	
	/**
	 * Getting usernames mentioned in Tweet from list of Mention(s) saved in Tweet Entity and looking in database for Users with matching usernames
	 * @param id - tweet id
	 * @return List<UserDto> - list of Users mentioned in that tweet 
	 */
	public List<UserDto> getMentions(Integer id) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false, false);
		if(tweet==null) return null;
		List<String> usernames = new ArrayList<>();
		tweet.getMentions().forEach(m -> usernames.add(m.getMention().substring(1)));
		List<UserDto> userList = userMapper.usersToUsersDto(userRepository.findByUsernameInAndDeleted(usernames, false));
		return userList!=null?sort.sortUsers(userList):null;
	}
	
	/**
	 * Using injected ContextFactory to build Context of current Tweet
	 * @param id - tweet id
	 * @return
	 */
	public Context getContext(Integer id) {
		return contextFactory.getContext(id);
	}
	
	/**
	 * Getting Hashtag(s) in this Tweet content from List of Hashtag(s) saved in Tweet Entity on Tweet creation
	 * @param id - tweet id
	 * @return
	 */
	public List<Hashtag> getTweetTags(Integer id) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false, false);
		if(tweet==null || tweet.getHashtag()==null) return null;
		return sort.sortHashtag(tweet.getHashtag());
	}
	
	
	/**
	 * Utility method for UserService component
	 * @param username
	 * @return List<TweetDto> from database based on author name
	 */
	public  List<TweetDto> authorTweets(String username){		
		List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweetRepository.findByAuthorUsernameAndDeleted(username, false));
		return tweetList!=null?sort.sortTweets(tweetList):null;
	}


		
}
