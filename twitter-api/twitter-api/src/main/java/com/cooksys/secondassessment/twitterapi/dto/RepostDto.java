package com.cooksys.secondassessment.twitterapi.dto;

import javax.persistence.Embeddable;


public class RepostDto {
	
	private Integer id;
	private UserDto author;
	private Long posted;
	private String content;

}
