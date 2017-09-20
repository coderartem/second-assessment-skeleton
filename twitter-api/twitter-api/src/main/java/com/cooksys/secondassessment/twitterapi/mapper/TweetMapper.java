package com.cooksys.secondassessment.twitterapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	TweetDto tweetToTweetDto (Tweet tweet);
	List<TweetDto> tweetsToTweetDtos (List<Tweet> tweets);
}
