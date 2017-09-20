package com.cooksys.secondassessment.twitterapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.TweetInput;
import com.cooksys.secondassessment.twitterapi.service.TweetService;

@RestController
@RequestMapping("tweets")
public class TweetController {
	
	
	
	private TweetService tS;

	public TweetController(TweetService tS) {
		this.tS = tS;
	}
	
	
	@GetMapping
	public List<TweetDto> getAllTweets(){
		return tS.getAll();
	}
	
	@PostMapping
	public TweetDto postTweet (@RequestBody TweetInput tweetInput){
		return tS.postTweet(tweetInput);
	}
	
	@GetMapping("tweets{id}")
	public TweetDto getThatTweet(@PathVariable Integer id){
		return tS.getThatTweet(id);
	}
	
	@DeleteMapping("tweets/{id}")
	public TweetDto deleteThisCrap(@PathVariable Integer id, @RequestBody Credentials cred){
		return tS.deleteThisCrap(id,cred);
	}
	
	@PostMapping("{id}/like")
	public void like(@PathVariable Integer id, @RequestBody Credentials cred){
		tS.like(id, cred);
	}
	
	@PostMapping("{id}/reply")
	public TweetDto reply(@PathVariable Integer id, @RequestBody TweetInput tweetIn){
		return tS.reply(id, tweetIn);
	}
	
	@PostMapping("{id}/repost")
	public TweetDto repost(@PathVariable Integer id, @RequestBody Credentials cred){
		return tS.repost(id, cred);
	}
	
	@GetMapping("{id}/likes")
	public List<UserDto> getTweetLikers(@PathVariable Integer id){
		return tS.getTweetLikers(id);
	}
	
	@GetMapping("{id}/replies")
	public List<TweetDto> getReplies(@PathVariable Integer id){
		return tS.getReplies(id);
	}
	
	@GetMapping("{id}/reposts")
	public List<TweetDto> getReposts(@PathVariable Integer id){
		return tS.getRepsots(id);
	}

}
