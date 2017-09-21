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

	List<Tweet> beforeList = new ArrayList<>();
	List<Tweet> afterList = new ArrayList<>();
	public Context getContext(Integer id){
		
		Tweet tweet = tR.findByIdAndDeletedAndAuthorDeleted(id, false, false);
		if(tweet==null) return null;
		before(tweet);
		after(tweet);
		
		beforeList.removeIf(b->b.equals(tweet));
		afterList.removeIf(a->a.equals(tweet));

		return new Context(tM.tweetsToTweetDtos(beforeList), tM.tweetToTweetDto(tweet), tM.tweetsToTweetDtos(afterList));
	}
	
	public List<Tweet> before(Tweet tweet){
		beforeList.add(tweet);
		if(tweet.getInReplyTo()==null){
			return beforeList;
		}
		return before(tweet.getInReplyTo());
	}
	
	public List<Tweet> after(Tweet tweet){
		afterList.add(tweet);
		if(tweet.getReplies()!=null){
			for(Tweet t : tweet.getReplies()){
				after(t);
			}
		}
		return afterList;
	}
}
