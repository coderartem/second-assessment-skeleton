package com.cooksys.secondassessment.twitterapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.entity.Hashtag;
import com.cooksys.secondassessment.twitterapi.service.HashtagService;

@RestController
@RequestMapping("tag")
public class HashtagController {
	
	private HashtagService tagService;

	public HashtagController(HashtagService tagService) {
		this.tagService = tagService;
	}
	
	@GetMapping
	public List<Hashtag> getAllTags(){
		return tagService.getAllTags();
	}
	
	@GetMapping("{label}")
	public List<TweetDto> getAllTweetsWithTag(@PathVariable String label){
		return tagService.getAllTweetsWithTag(label);
	}

}
