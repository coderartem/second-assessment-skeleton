package com.cooksys.secondassessment.twitterapi.servlet.response;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;

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

}
