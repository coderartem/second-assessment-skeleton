package com.cooksys.secondassessment.twitterapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.converter.TweetCreator;
import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.TweetInput;
import com.cooksys.secondassessment.twitterapi.mapper.TweetMapper;
import com.cooksys.secondassessment.twitterapi.mapper.UserMapper;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

@Service
public class TweetService {
	
	private TweetMapper tM;
	private UserRepository uR;
	private TweetRepository tR;
	private UserMapper uM;

	public TweetService(TweetMapper tM,  UserRepository uR, TweetRepository tR, UserMapper uM) {
		this.tM = tM;
		this.uR = uR;
		this.tR = tR;
		this.uM = uM;
	}


	public List<TweetDto> getAll() {		//!
		return tM.tweetsToTweetDtos(tR.findByDeleted(false));
	}

	public TweetDto postTweet(TweetInput tweetInput) {
		Tweet tweet = new TweetCreator(tweetInput, uR).createTweet();   //Zamenit' na Mapper
		tR.save(tweet);
		return tM.tweetToTweetDto(tweet);
	}

	public TweetDto getThatTweet(Integer id) {		//!
		return tM.tweetToTweetDto(tR.findByIdAndDeletedAndAuthorDeleted(id,false,false));
	}

	@Transactional
	public TweetDto deleteThisCrap(Integer id, Credentials cred) {		//!
		Tweet tweet = tR.findByAuthorCredentialsAndIdAndDeletedAndAuthorDeleted(cred, id,false,false);
		if(tweet!=null){
			tweet.setDeleted(true);
			return tM.tweetToTweetDto(tweet);
		}
		return null;
	}

	@Transactional
	public int like(Integer id, Credentials cred) {		//!
		Tweet tweet = tR.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		Users user = uR.findByCredentialsAndDeleted(cred, false);
		if(tweet!=null && user!=null){
				tweet.getLikedBy().add(user);
				user.getLiked().add(tweet);
				return 1;
		}
		return 0;
		
	}

	@Transactional
	public TweetDto reply(Integer id, TweetInput tweetIn) {
		Tweet tweet = new TweetCreator(tweetIn, uR).createTweet();  //Zamenit' na Mapper
		Tweet inReplyTo = tR.findByIdAndDeletedAndAuthorDeleted(id,false,false);
		if(inReplyTo!=null){
			inReplyTo.getReplies().add(tweet);
			tweet.setInReplyTo(inReplyTo);
			return tM.tweetToTweetDto(tweet);
		}
		return null;
	}
	
	@Transactional
	public TweetDto repost(Integer id, Credentials cred) {		//!
		Users user = uR.findByCredentialsAndDeleted(cred, false);
		Tweet tweet = tR.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		
		TweetInput input = new TweetInput();				//Zamenit' na norm Tweet Factory
		input.setCredentials(cred);								//Need credentials check
		input.setContent("");
		Tweet repost;
		if(tweet!=null && user!=null){
			repost = new TweetCreator(input, uR).createTweet();
			repost.setRepostOf(tweet);
			return tM.tweetToTweetDto(repost);
		}
		return null;
	}
	
	public List<UserDto> getTweetLikers(Integer id) {
		if(tR.findByIdAndDeletedAndAuthorDeleted(id, false,false)!=null){
			return uM.usersToUsersDto(uR.findByDeletedAndLikedId(false,id)); //HZ ne testil (mozhno sozdat' List  of likers on Tweet esli eto ne budet rabotat')
		}
		return null;
	}
	
	public List<TweetDto> getReplies(Integer id) {
		Tweet tweet = tR.findByIdAndDeletedAndAuthorDeleted(id, false,false);
//		List<TweetDto> res = new ArrayList<>();
		if(tweet!=null){
			
//			List<Tweet> parrent = tR.findByInReplyTo(tweet);			//Other way
//			for(Tweet x : parrent){
//				res.add(tM.tweetToTweetDto(x.getInReplyTo()));
//			}
//			return res;
			
			return tM.tweetsToTweetDtos(tweet.getReplies());
		}
		return null;
	}
	
	public List<TweetDto> getRepsots(Integer id) {		//!
		Tweet tweet = tR.findByIdAndDeletedAndAuthorDeleted(id, false,false);
		if(tweet!=null){
			return tM.tweetsToTweetDtos(tR.findByRepostOf(tweet));
		}
		return null;
	}
	
	
	public  List<TweetDto> authorTweets(String username){		//!
		return tM.tweetsToTweetDtos(tR.findByAuthorUsernameAndDeleted(username, false));
	}


	


	


	


	
	
	
}
