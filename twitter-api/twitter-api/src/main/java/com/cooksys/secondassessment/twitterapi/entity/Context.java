package com.cooksys.secondassessment.twitterapi.entity;

import java.util.List;

import com.cooksys.secondassessment.twitterapi.dto.TweetDto;

public class Context {
	
	private TweetDto target;
	private List<TweetDto> before;
	private List<TweetDto> after;
	
	
	public Context() {
		// TODO Auto-generated constructor stub
	}
	
	public Context(List<TweetDto> before, TweetDto target, List<TweetDto> after) {
		this.before = before;
		this.after = after;
		this.target = target;
	}
	
	public TweetDto getTarget() {
		return target;
	}
	public List<TweetDto> getBefore() {
		return before;
	}
	public List<TweetDto> getAfter() {
		return after;
	}
	public void setTarget(TweetDto target) {
		this.target = target;
	}
	public void setBefore(List<TweetDto> before) {
		this.before = before;
	}
	public void setAfter(List<TweetDto> after) {
		this.after = after;
	}

}
