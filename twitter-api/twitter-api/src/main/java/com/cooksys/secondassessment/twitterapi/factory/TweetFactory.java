package com.cooksys.secondassessment.twitterapi.factory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Hashtag;
import com.cooksys.secondassessment.twitterapi.entity.Mention;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.entity.Users;
import com.cooksys.secondassessment.twitterapi.input.dto.TweetInput;
import com.cooksys.secondassessment.twitterapi.repository.HashTagRepository;
import com.cooksys.secondassessment.twitterapi.repository.MentionRepsoitory;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;

/**
 * 
 * @author Artem Kolin
 * 
 * This component designed to create based on input parameters and delete Tweet(s) as well as process Hashtag(s) and Mention(s) from content
 *
 */
@Component
public class TweetFactory {
	
	private UserRepository userRepository;
	private TweetRepository tweetRepository;
	private HashTagRepository hashTagRepository;
	private MentionRepsoitory mentionRepsoitory;
	
	public TweetFactory( UserRepository userRepository, TweetRepository tweetRepository, HashTagRepository hashTagRepository, 
			MentionRepsoitory mentionRepsoitory) {
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
		this.hashTagRepository = hashTagRepository;
		this.mentionRepsoitory = mentionRepsoitory;
	}
	
	/**
	 * Creating new Tweet, setting up values based on input
	 * @param tweetInput
	 * @return Tweet
	 */
	
	public Tweet createNewTweet(TweetInput tweetInput){
			Tweet tweet = new Tweet();
			Users user = userRepository.findByCredentialsAndDeleted(tweetInput.getCredentials(), false);  //Checking if user exists and active
			if(user==null) return null;
			
			tweet.setAuthor(user); 
			tweet.setPosted(new Timestamp(System.currentTimeMillis()).getTime());
			tweet.setContent(tweetInput.getContent());
			tweet.setDeleted(false);
			tweet.setHashtag(tagsCheck(tweetInput.getContent()));
			tweet.setMentions(mentionsCheck(tweetInput.getContent()));
			
			tweetRepository.saveAndFlush(tweet);
			
		return tweet;
	}
	
	/**
	 * Looking for Hashtag(s) inside Tweet and matching that tag to already existing tags
	 * Creating new if it doesn't exist
	 * @param content - Tweet content
	 * @return List<Hashtag> - list of Hashtag(s) found
	 */
	
	@Transactional
	private List<Hashtag> tagsCheck(String content){
		
		Matcher hashTag = Pattern.compile("#(\\w+)").matcher(content);
		List<Hashtag> tagsList = new ArrayList<>();
		while(hashTag.find()){
			Hashtag existingHashtag = hashTagRepository.findByLabel(hashTag.group());
			if(existingHashtag!=null){
				existingHashtag.setLastUsed(new Timestamp(System.currentTimeMillis()).getTime());
				tagsList.add(existingHashtag);
			}else{
				Hashtag newHashtag = new Hashtag(hashTag.group());
				hashTagRepository.saveAndFlush(newHashtag);
				tagsList.add(newHashtag);
			}
	}
		return tagsList;
	}
	
	/**
	 * Looking for Mention(s) inside Tweet and matching that mention to existing users
	 * Creating new if it doesn't exist
	 * @param content - Tweet content
	 * @return List<Mention> - list of Mention(s) found
	 */
	
	@Transactional
	private List<Mention> mentionsCheck(String content){
		
		Matcher mention = Pattern.compile("@(\\w+)").matcher(content);
		List<Mention> mentionsList = new ArrayList<>();
		while(mention.find()){
			
			Mention existingMention = mentionRepsoitory.findByMention(mention.group());
			if(existingMention!=null){
				mentionsList.add(existingMention);
			}else{
				if(userRepository.findByUsernameAndDeleted(mention.group().substring(1), false)==null){
					continue;
				}
				Mention newMention = new Mention(mention.group());
				mentionRepsoitory.saveAndFlush(newMention);
				mentionsList.add(newMention);
			}
	}
		return mentionsList;
	}
	
	//Deletion of Tweet
	
	@Transactional
	public Tweet deleteTweet(Integer id, Credentials cred){
		Tweet tweet = tweetRepository.findByAuthorCredentialsAndIdAndDeletedAndAuthorDeleted(cred, id,false,false);  
		if(tweet!=null){ 		//Checking if Tweet exists and active and Author exitsts and active
			tweet.setDeleted(true); 		//Disabling Tweet
			return tweet;
		}
		return null;
	}
	
	//Creation of repostTweet (content is empty)
	public Tweet createRepostTweet(Credentials cred){
		TweetInput input = new TweetInput("",cred);
		return  createNewTweet(input);
	}
}