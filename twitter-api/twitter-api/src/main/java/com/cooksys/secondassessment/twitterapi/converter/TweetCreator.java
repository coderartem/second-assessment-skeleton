package com.cooksys.secondassessment.twitterapi.converter;

import java.sql.Timestamp;

import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.input.dto.TweetInput;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

public class TweetCreator {
	
	private TweetInput tweetIn;
	private Tweet tweet = new Tweet();
	private UserRepository uR;
	
	public TweetCreator(TweetInput tweetIn, UserRepository uR) {
		this.tweetIn = tweetIn;
		this.uR = uR;
	}
	
	public Tweet createTweet(){	
		System.out.println(uR.findByCredentials(tweetIn.getCredentials()));
		tweet.setAuthor(uR.findByCredentials(tweetIn.getCredentials()));  //Null check needed
		tweet.setPosted(new Timestamp(System.currentTimeMillis()).getTime());
		tweet.setContent(tweetIn.getContent());
		tweet.setDeleted(false);
		
		return tweet;
	}

}
