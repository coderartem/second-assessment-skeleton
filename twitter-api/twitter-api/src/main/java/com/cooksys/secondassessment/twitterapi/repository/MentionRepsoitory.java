package com.cooksys.secondassessment.twitterapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.twitterapi.entity.Mention;

public interface MentionRepsoitory extends JpaRepository<Mention,Integer> {

	Mention findByMention(String string);
	

}
