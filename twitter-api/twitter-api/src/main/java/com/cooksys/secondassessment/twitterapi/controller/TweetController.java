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
import com.cooksys.secondassessment.twitterapi.entity.Credentials;
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

}
