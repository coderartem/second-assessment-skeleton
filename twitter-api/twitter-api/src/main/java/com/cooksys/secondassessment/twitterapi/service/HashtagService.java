package com.cooksys.secondassessment.twitterapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.entity.Hashtag;
import com.cooksys.secondassessment.twitterapi.mapper.TweetMapper;
import com.cooksys.secondassessment.twitterapi.repository.HashTagRepository;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;

@Service
public class HashtagService {

	private HashTagRepository hashTagRepository;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;

	public HashtagService(HashTagRepository hashTagRepository, TweetRepository tweetRepository, TweetMapper tweetMapper) {
		this.hashTagRepository = hashTagRepository;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}

	public List<Hashtag> getAllTags() {
		return hashTagRepository.findAll();
	}

	public List<TweetDto> getAllTweetsWithTag(String label) {
		return tweetMapper.tweetsToTweetDtos(tweetRepository.findByDeletedAndHashtagLabel(false, label));
	}
	
	
	
}
