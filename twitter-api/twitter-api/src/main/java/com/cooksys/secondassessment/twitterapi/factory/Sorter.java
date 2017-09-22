package com.cooksys.secondassessment.twitterapi.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Hashtag;
import com.cooksys.secondassessment.twitterapi.entity.Mention;

/**
 * 
 * @author Artem Kolin
 * 
 * This component serves as sorter of output lists of TweetDto, UserDto, Hashtag and Mention objects
 *
 */
@Component
public class Sorter {
	
	
	public List<TweetDto> sortTweets(List<TweetDto> tweets){
		tweets.sort((a,b)->b.getPosted().compareTo(a.getPosted()));
		return tweets;
	}
	
	public List<UserDto> sortUsers(List<UserDto> users){
		users.sort((a,b)->b.getJoined().compareTo(a.getJoined()));
		return users;
	}
	
	public List<Hashtag> sortHashtag(List<Hashtag> tags){
		tags.sort((a,b)->b.getLastUsed().compareTo(a.getLastUsed()));
		return tags;
	}
	
	public List<Mention> sortMentions(List<Mention> mentions){
		mentions.sort((a,b)->a.getMention().compareTo(b.getMention()));
		return mentions;
	}

}
