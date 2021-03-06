package com.cooksys.secondassessment.twitterapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet,Integer> {

	Tweet findByIdAndDeletedAndAuthorDeleted(Integer id, Boolean tweetDeleted, Boolean authorDeleted);
	List<Tweet> findByDeleted(Boolean deleted);
	Tweet findByAuthorCredentialsAndIdAndDeletedAndAuthorDeleted(Credentials cred, Integer id, Boolean tweetDeleted, Boolean authorDeleted);
	List<Tweet> findByAuthorUsernameAndDeleted(String username, Boolean deleted);
	List<Tweet> findByDeletedAndHashtagLabel (Boolean deleted, String label);
	List<Tweet> findByDeletedAndMentionsMention (Boolean deleted, String mention);
	List<Tweet> findByRepostOfAndDeleted(Tweet tweet, boolean b);
	
}
