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
	
	
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private Sorter sort;
	
	private List<Tweet> beforeList = new ArrayList<>();
	private List<Tweet> afterList = new ArrayList<>();
	


	public ContextFactory(TweetRepository tweetRepository, TweetMapper tweetMapper, Sorter sort) {
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.sort = sort;
	}

	public Context getContext(Integer id){
		
		beforeList.clear();
		afterList.clear();
		
		Tweet tweet = tweetRepository.findByIdAndDeletedAndAuthorDeleted(id, false, false);
		if(tweet==null) return null;
		before(tweet);
		after(tweet);
		
		beforeList.removeIf(b->b.equals(tweet));
		beforeList.removeIf(b->b.isDeleted());
		afterList.removeIf(a->a.equals(tweet));
		afterList.removeIf(a->a.isDeleted());

		return new Context(sort.sortTweets(tweetMapper.tweetsToTweetDtos(beforeList)), tweetMapper.tweetToTweetDto(tweet), 
				sort.sortTweets(tweetMapper.tweetsToTweetDtos(afterList)));
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
