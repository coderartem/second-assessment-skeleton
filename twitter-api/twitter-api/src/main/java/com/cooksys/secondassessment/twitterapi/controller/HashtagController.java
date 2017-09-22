package com.cooksys.secondassessment.twitterapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;
import com.cooksys.secondassessment.twitterapi.entity.Hashtag;
import com.cooksys.secondassessment.twitterapi.service.HashtagService;
import com.cooksys.secondassessment.twitterapi.servlet.response.ServletResponse;

/**
 * 
 * @author Artem Kolin 
 * 
 * Sending response (not everywhere) from TweetService to ServletResponse component to setup HttpServletResponse to 404 in case returned value is null
 *
 */

@RestController
@RequestMapping("tag")
public class HashtagController {
	
	private HashtagService tagService;
	private ServletResponse servletResponse;

	public HashtagController(HashtagService tagService, ServletResponse servletResponse) {
		this.tagService = tagService;
		this.servletResponse = servletResponse;
	}
	
	@GetMapping
	public List<Hashtag> getAllTags(HttpServletResponse response){
		return servletResponse.listOfTagsNullCheck(tagService.getAllTags(), response);
	}
	
	@GetMapping("{label}")
	public List<TweetDto> getAllTweetsWithTag(@PathVariable String label, HttpServletResponse response){
		return servletResponse.listOfTweetsNullCheck(tagService.getAllTweetsWithTag(label), response);
	}

}
