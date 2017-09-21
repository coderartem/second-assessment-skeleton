package com.cooksys.secondassessment.twitterapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Context;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Mention;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.factory.ContextFactory;
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

	public TweetService(TweetMapper tweetMapper,  UserRepository userRepository, TweetRepository tweetRepository, UserMapper userMapper, TweetFactory tweetFactory, ContextFactory contextFactory) {
		this.tweetMapper = tweetMapper;
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
		this.userMapper = userMapper;
		this.tweetFactory = tweetFactory;
		this.contextFactory = contextFactory;
	}


	public List<TweetDto> getAll() {		//!
		return tweetMapper.tweetsToTweetDtos(tweetRepository.findByDeleted(false));
	}

	public TweetDto postTweet(TweetInput tweetInput) {
		if(userRepository.findByCredentialsAndDeleted(tweetInput.getCredentials(), false)!=null){
		return tweetMapper.tweetToTweetDto(tweetFactory.createTweet(tweetInput));
		}return null;
	}

	public TweetDto getThatTweet(Integer id) {		//!
		return tweetMapper.tweetToTweetDto(tweetRepository.findByIdAndDeletedAndAuthorDeleted(id,false,false));
	}

	@Transactional
	public TweetDto deleteThisCrap(Integer id, Credentials cred) {		//!
			return tweetMapper.tweetToTweetDto(tweetFactory.deleteTweet(id, cred));
	}

	@Transactional
	public boolean like(Integer id, Credentials cred) {		//!
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		Users user = userRepository.findByCredentialsAndDeleted(cred, false);
		if(tweet!=null && user!=null){
				tweet.getLikedBy().add(user);
				user.getLiked().add(tweet);
			return true;
		}
		return false;
		
	}

	@Transactional
	public TweetDto reply(Integer id, TweetInput tweetIn) {
		if(userRepository.findByCredentialsAndDeleted(tweetIn.getCredentials(), false)==null) return null;
		Tweet tweet;
		Tweet inReplyTo = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id,false,false);
		if(inReplyTo!=null){
				tweet = tweetFactory.createTweet(tweetIn);
				tweetRepository.saveAndFlush(tweet);
				inReplyTo.getReplies().add(tweet);
				tweet.setInReplyTo(inReplyTo);
			return tweetMapper.tweetToTweetDto(tweet);
		}
		return null;
	}
	
	@Transactional
	public TweetDto repost(Integer id, Credentials cred) {		//!
		Users user = userRepository.findByCredentialsAndDeleted(cred, false);
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		
		TweetInput input = new TweetInput();				//Zamenit' na norm Tweet Factory
		input.setCredentials(cred);								//Need credentials check
		input.setContent("");
		Tweet repost;
		if(tweet!=null && user!=null){
			repost = tweetFactory.createTweet(input);
			repost.setRepostOf(tweet);
			tweetRepository.saveAndFlush(repost);
			return tweetMapper.tweetToTweetDto(repost);
		}
		return null;
	}
	
	public List<UserDto> getTweetLikers(Integer id) {
		if(tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false)!=null){
			return userMapper.usersToUsersDto(userRepository.findByDeletedAndLikedId(false,id)); //HZ ne testil (mozhno sozdat' List  of likers on Tweet esli eto ne budet rabotat')
		}
		return null;
	}
	
	public List<TweetDto> getReplies(Integer id) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		if(tweet!=null){
			return tweetMapper.tweetsToTweetDtos(tweet.getReplies());
		}
		return null;
	}
	
	public List<TweetDto> getRepsots(Integer id) {		//!
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		if(tweet!=null){
			return tweetMapper.tweetsToTweetDtos(tweetRepository.findByRepostOf(tweet));
		}
		return null;
	}
	
	public List<UserDto> getMentions(Integer id) {
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false, false);
		if(tweet==null) return null;
		List<String> usernames = new ArrayList<>();			//Ugly !!!!!!!!!!
		for(Mention x : tweet.getMentions()){
			usernames.add(x.getMention().substring(1));
		}		
		return userMapper.usersToUsersDto(userRepository.findByUsernameInAndDeleted(usernames, false));
	}
	
	public Context getContext(Integer id) {
		return contextFactory.getContext(id);
	}
	
	public  List<TweetDto> authorTweets(String username){		//!
		return tweetMapper.tweetsToTweetDtos(tweetRepository.findByAuthorUsernameAndDeleted(username, false));
	}


	


	

	
}
