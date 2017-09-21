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
import com.cooksys.secondassessment.twitterapi.entity.Mention;
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

	public TweetDto postTweet(TweetInput tweetInput) {
		if(userRepository.findByCredentialsAndDeleted(tweetInput.getCredentials(), false)==null)return null;
		return tweetMapper.tweetToTweetDto(tweetFactory.createTweet(tweetInput));
	}

	public TweetDto getThatTweet(Integer id) {		
		return tweetMapper.tweetToTweetDto(tweetRepository.findByIdAndDeletedAndAuthorDeleted(id,false,false));
	}

	@Transactional
	public TweetDto deleteThisCrap(Integer id, Credentials cred) {
			return tweetMapper.tweetToTweetDto(tweetFactory.deleteTweet(id, cred));
	}

	@Transactional
	public boolean like(Integer id, Credentials cred) {	
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		Users user = userRepository.findByCredentialsAndDeleted(cred, false);
		if(tweet==null || user==null)return false;
				tweet.getLikedBy().add(user);
				user.getLiked().add(tweet);
			return true;
	}

	@Transactional
	public TweetDto reply(Integer id, TweetInput tweetInput) {
		Tweet inReplyTo = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id,false,false);
		if(inReplyTo==null || userRepository.findByCredentialsAndDeleted(tweetInput.getCredentials(), false)==null)return null;
		Tweet tweet = tweetFactory.createTweet(tweetInput);
				inReplyTo.getReplies().add(tweet);
				tweet.setInReplyTo(inReplyTo);
			return tweetMapper.tweetToTweetDto(tweet);
	}
	
	@Transactional
	public TweetDto repost(Integer id, Credentials cred) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		if(tweet==null || userRepository.findByCredentialsAndDeleted(cred, false)==null) return null;
				Tweet repost = tweetFactory.createRepostTweet(cred);
				repost.setRepostOf(tweet);
			return tweetMapper.tweetToTweetDto(repost);
	}
	
	public List<UserDto> getTweetLikers(Integer id) {
		if(tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false)==null)return null;
			List<UserDto> userList = userMapper.usersToUsersDto(userRepository.findByDeletedAndLikedId(false,id));
			return userList!=null?sort.sortUsers(userList):null;
	}
	
	public List<TweetDto> getReplies(Integer id) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		if(tweet==null)return null;
			List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweet.getReplies());
			return tweetList!=null?sort.sortTweets(tweetList):null;
	}
	
	public List<TweetDto> getRepsots(Integer id) {	
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		if(tweet==null)return null;
			List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweetRepository.findByRepostOf(tweet));
			return tweetList!=null?sort.sortTweets(tweetList):null;
	}
	
	public List<UserDto> getMentions(Integer id) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false, false);
		if(tweet==null) return null;
		List<String> usernames = new ArrayList<>();
		tweet.getMentions().forEach(m -> usernames.add(m.getMention().substring(1)));
		List<UserDto> userList = userMapper.usersToUsersDto(userRepository.findByUsernameInAndDeleted(usernames, false));
		return userList!=null?sort.sortUsers(userList):null;
	}
	
	public Context getContext(Integer id) {
		return contextFactory.getContext(id);
	}
	
	public List<Hashtag> getTweetTags(Integer id) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false, false);
		if(tweet==null || tweet.getHashtag()==null) return null;
		return sort.sortHashtag(tweet.getHashtag());
	}
	
	public  List<TweetDto> authorTweets(String username){		
		List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweetRepository.findByAuthorUsernameAndDeleted(username, false));
		return tweetList!=null?sort.sortTweets(tweetList):null;
	}


		
}
