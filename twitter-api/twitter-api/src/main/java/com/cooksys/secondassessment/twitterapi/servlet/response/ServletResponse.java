package com.cooksys.secondassessment.twitterapi.servlet.response;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Hashtag;


/**
 * 
 * @author Artem Kolin
 * 
 * This component created to set HttpServletResponse to 404 NOT FOUND in case of null returned on user request
 *
 */
@Component
public class ServletResponse {
	
	
	
	public UserDto userNullCheck(UserDto user, HttpServletResponse response){
		if(user==null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return user;
	}
	
	public TweetDto tweetNullCheck(TweetDto tweet, HttpServletResponse response){
		if(tweet==null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return tweet;
	}
	
	public List<UserDto> listOfUsersNullCheck(List<UserDto> userList, HttpServletResponse response){
		if(userList==null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return userList;
	}
	
	public List<TweetDto> listOfTweetsNullCheck(List<TweetDto> tweetList, HttpServletResponse response){
		if(tweetList==null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return tweetList;
	}
	
	public List<Hashtag> listOfTagsNullCheck(List<Hashtag> tagsList, HttpServletResponse response){
		if(tagsList==null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return tagsList;
	}

}
