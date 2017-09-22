package com.cooksys.secondassessment.twitterapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.entity.Hashtag;
import com.cooksys.secondassessment.twitterapi.factory.Sorter;
import com.cooksys.secondassessment.twitterapi.mapper.TweetMapper;
import com.cooksys.secondassessment.twitterapi.repository.HashTagRepository;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
/**
 * 
 * @author Artem Kolin
 * 
 * Names of methods are pretty informative themselves
 * Implementing everywhere null checks on returned from data base information (Hashtag(s), Tweet(s))
 * Sending List<> results to Sorter injected component to sort them in DESC order based on timestamps
 */
@Service
public class HashtagService {

	private HashTagRepository hashTagRepository;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private Sorter sort;

	public HashtagService(HashTagRepository hashTagRepository, TweetRepository tweetRepository, TweetMapper tweetMapper, Sorter sort) {
		this.hashTagRepository = hashTagRepository;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.sort = sort;
	}

	public List<Hashtag> getAllTags() {
		List<Hashtag> tagsList = hashTagRepository.findAll();
		return tagsList!=null?sort.sortHashtag(tagsList):null;
	}

	public List<TweetDto> getAllTweetsWithTag(String label) {
		List<TweetDto> tweetList = tweetMapper.tweetsToTweetDtos(tweetRepository.findByDeletedAndHashtagLabel(false, label));
		return tweetList!=null?sort.sortTweets(tweetList):null;
	}
		
}
