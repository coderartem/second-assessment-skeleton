package com.cooksys.secondassessment.twitterapi.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
public class Tweet {

//	@Id
//	@GeneratedValue
	private Integer id;
	private Users author;
	private Long posted;
	private String content;
	
	
	private Tweet inReplayTo;
	
	private Tweet repostOf;
}
