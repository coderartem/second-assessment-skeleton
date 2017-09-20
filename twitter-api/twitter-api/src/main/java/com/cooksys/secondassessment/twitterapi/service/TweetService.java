package com.cooksys.secondassessment.twitterapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.converter.TweetCreator;
import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.TweetInput;
import com.cooksys.secondassessment.twitterapi.mapper.TweetMapper;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class TweetService {
	
	private TweetMapper tM;
	private UserRepository uR;
	private TweetRepository tR;

	public TweetService(TweetMapper tM,  UserRepository uR, TweetRepository tR) {
		this.tM = tM;
		this.uR = uR;
		this.tR = tR;
	}


	public List<TweetDto> getAll() {
		return tM.tweetsToTweetDtos(tR.findByDeleted(false));
	}

	public TweetDto postTweet(TweetInput tweetInput) {
		Tweet tweet = new TweetCreator(tweetInput, uR).createTweet();   //Zamenit' na Mapper
		tR.save(tweet);
		return tM.tweetToTweetDto(tweet);
	}

	public TweetDto getThatTweet(Integer id) {
		
		return tM.tweetToTweetDto(tR.findOne(id));
	}

	@Transactional
	public TweetDto deleteThisCrap(Integer id, Credentials cred) {
		Tweet tweet = tR.findByAuthorCredentialsAndId(cred, id);
		if(tweet!=null){
			tweet.setDeleted(true);
			return tM.tweetToTweetDto(tweet);
		}
		return null;
	}

	@Transactional
	public void like(Integer id, Credentials cred) {
		Tweet tweet = tR.findOne(id);
		if(tweet!=null && !tweet.isDeleted()){
			
		}
		
	}
	
	
}
