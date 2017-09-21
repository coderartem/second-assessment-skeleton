package com.cooksys.secondassessment.twitterapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Context;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.input.dto.TweetInput;
import com.cooksys.secondassessment.twitterapi.service.TweetService;
import com.cooksys.secondassessment.twitterapi.servlet.response.ServletResponse;

@RestController
@RequestMapping("tweets")
public class TweetController {
	
	
	
	private TweetService tS;
	private ServletResponse sR;

	public TweetController(TweetService tS, ServletResponse sR) {
		this.tS = tS;
		this.sR = sR;
	}
	
	
	@GetMapping
	public List<TweetDto> getAllTweets(){
		return tS.getAll();
	}
	
	@PostMapping
	public TweetDto postTweet (@RequestBody TweetInput tweetInput, HttpServletResponse response){
		return sR.tweetNullCheck(tS.postTweet(tweetInput), response);
	}
	
	@GetMapping("{id}")
	public TweetDto getThatTweet(@PathVariable Integer id, HttpServletResponse response){
		return sR.tweetNullCheck(tS.getThatTweet(id), response);
	}
	
	@DeleteMapping("{id}")
	public TweetDto deleteThisCrap(@PathVariable Integer id, @RequestBody Credentials cred, HttpServletResponse response){
		return sR.tweetNullCheck(tS.deleteThisCrap(id,cred), response);
	}
	
	@PostMapping("{id}/like")
	public void like(@PathVariable Integer id, @RequestBody Credentials cred, HttpServletResponse response){
		boolean res = tS.like(id, cred);
		response.setStatus(res?HttpServletResponse.SC_OK:HttpServletResponse.SC_NOT_FOUND);
	}
	
	@PostMapping("{id}/reply")
	public TweetDto reply(@PathVariable Integer id, @RequestBody TweetInput tweetIn, HttpServletResponse response){
		return sR.tweetNullCheck(tS.reply(id, tweetIn), response);
	}
	
	@PostMapping("{id}/repost")
	public TweetDto repost(@PathVariable Integer id, @RequestBody Credentials cred, HttpServletResponse response){
		return sR.tweetNullCheck(tS.repost(id, cred), response);
	}
	
	@GetMapping("{id}/likes")
	public List<UserDto> getTweetLikers(@PathVariable Integer id, HttpServletResponse response){
		return sR.listOfUsersNullCheck(tS.getTweetLikers(id), response);
	}
	
	@GetMapping("{id}/replies")
	public List<TweetDto> getReplies(@PathVariable Integer id, HttpServletResponse response){
		return sR.listOfTweetsNullCheck(tS.getReplies(id), response);
	}
	
	@GetMapping("{id}/reposts")
	public List<TweetDto> getReposts(@PathVariable Integer id, HttpServletResponse response){
		return sR.listOfTweetsNullCheck(tS.getRepsots(id), response);
	}
	
	@GetMapping("{id}/mentions")
	public List<UserDto> getMentions(@PathVariable Integer id, HttpServletResponse response){
		return sR.listOfUsersNullCheck(tS.getMentions(id), response);
	}
	
	@GetMapping("{id}/context")
	public Context getContext(@PathVariable Integer id, HttpServletResponse response){
		return tS.getContext(id);
	}

}
