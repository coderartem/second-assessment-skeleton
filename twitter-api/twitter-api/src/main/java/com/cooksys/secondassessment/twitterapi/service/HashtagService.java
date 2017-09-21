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

	private HashTagRepository hR;
	private TweetRepository tR;
	private TweetMapper tM;

	public HashtagService(HashTagRepository hR, TweetRepository tR, TweetMapper tM) {
		this.hR = hR;
		this.tR = tR;
		this.tM = tM;
	}

	public List<Hashtag> getAllTags() {
		return hR.findAll();
	}

	public List<TweetDto> getAllTweetsWithTag(String label) {
		return tM.tweetsToTweetDtos(tR.findByDeletedAndHashtagLabel(false, label));
	}
	
	
	
}
