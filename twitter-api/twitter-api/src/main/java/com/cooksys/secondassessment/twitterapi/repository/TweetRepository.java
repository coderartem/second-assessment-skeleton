package com.cooksys.secondassessment.twitterapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet,Integer> {

	List<Tweet> findByDeleted(Boolean deleted);
	Tweet findByAuthorCredentialsAndId(Credentials cred, Integer id);
}
