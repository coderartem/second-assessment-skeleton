package com.cooksys.secondassessment.twitterapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.twitterapi.dto.ReplyTweetDto;
import com.cooksys.secondassessment.twitterapi.dto.RepostTweetDto;
import com.cooksys.secondassessment.twitterapi.dto.SimpleTweetDto;
import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	TweetDto tweetToTweetDto (Tweet tweet);
	List<TweetDto> tweetsToTweetDtos (List<Tweet> tweets);
	
	SimpleTweetDto tweetToSimpleDto(Tweet tweet);
	RepostTweetDto tweetToRepostDto(Tweet tweet);
	ReplyTweetDto tweetToReplyDto(Tweet tweet);
	
	List<SimpleTweetDto> tweetToSimpleDto(List<Tweet> tweets);
	List<RepostTweetDto> tweetToRepostDto(List<Tweet> tweets);
	List<ReplyTweetDto> tweetToReplyDto(List<Tweet> tweets);
}
