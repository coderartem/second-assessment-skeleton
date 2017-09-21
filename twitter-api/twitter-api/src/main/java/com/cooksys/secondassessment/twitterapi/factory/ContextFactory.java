package com.cooksys.secondassessment.twitterapi.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cooksys.secondassessment.twitterapi.entity.Context;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.mapper.TweetMapper;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;


@Component
public class ContextFactory {
	
	
	private TweetRepository tR;
	private TweetMapper tM;


	public ContextFactory(TweetRepository tR, TweetMapper tM) {
		this.tR = tR;
		this.tM = tM;
	}

	List<Tweet> before = new ArrayList<>();
	List<Tweet> after = new ArrayList<>();
	public Context getContext(Integer id){
		
		Tweet tweet = tR.findByIdAndDeletedAndAuthorDeleted(id, false, false);
		if(tweet==null) return null;
		before(tweet);
		after(tweet);

		return new Context(tM.tweetsToTweetDtos(before), tM.tweetToTweetDto(tweet), tM.tweetsToTweetDtos(after));
		
	}
	//nado izbavitsya ot dublikatov
	
	public List<Tweet> before(Tweet tweet){
		before.add(tweet);
		if(tweet.getInReplyTo()==null){
			return before;
		}
		return before(tweet.getInReplyTo());
	}
	
	public List<Tweet> after(Tweet tweet){
		after.add(tweet);
		if(tweet.getReplies()!=null){
			for(Tweet t : tweet.getReplies()){
				after(t);
			}
		}
		return after;
	}
}
