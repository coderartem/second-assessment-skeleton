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


@Component
public class TweetFactory {
	
	private UserRepository userRepository;
	private TweetRepository tweetRepository;
	private HashTagRepository hashTagRepository;
	private MentionRepsoitory mentionRepsoitory;
	
	public TweetFactory( UserRepository userRepository, TweetRepository tweetRepository, HashTagRepository hashTagRepository, MentionRepsoitory mentionRepsoitory) {
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
		this.hashTagRepository = hashTagRepository;
		this.mentionRepsoitory = mentionRepsoitory;
	}
	
	public Tweet createTweet(TweetInput tweetIn){
			Tweet tweet = new Tweet();
			Users user = userRepository.findByCredentialsAndDeleted(tweetIn.getCredentials(), false);
			if(user==null) return null;
			
			tweet.setAuthor(user); 
			tweet.setPosted(new Timestamp(System.currentTimeMillis()).getTime());
			tweet.setContent(tweetIn.getContent());
			tweet.setDeleted(false);
			tweet.setHashtag(tagsCheck(tweetIn.getContent()));
			tweet.setMentions(mentionsCheck(tweetIn.getContent()));
			
			tweetRepository.saveAndFlush(tweet);
			
		return tweet;
	}
	
	public List<Hashtag> tagsCheck(String content){
		
		Matcher hashTag = Pattern.compile("#(\\w+)").matcher(content);
		List<Hashtag> hL = new ArrayList<>();
		while(hashTag.find()){
			Hashtag existingHashtag = hashTagRepository.findByLabel(hashTag.group());
			if(existingHashtag!=null){
				existingHashtag.setLastUsed(new Timestamp(System.currentTimeMillis()).getTime());
				hL.add(existingHashtag);
			}else{
				
				Hashtag newHashtag = new Hashtag();
				newHashtag.setLabel(hashTag.group());
				newHashtag.setLastUsed(newHashtag.getFirstUsed());
				hashTagRepository.saveAndFlush(newHashtag);
				hL.add(newHashtag);
			}
	}
		return hL;
	}
	
	public List<Mention> mentionsCheck(String content){
		
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
				Mention newMention = new Mention();
				newMention.setMention(mention.group());
				mentionRepsoitory.saveAndFlush(newMention);
				mentionsList.add(newMention);
			}
	}
		return mentionsList;
	}
	
	@Transactional
	public Tweet deleteTweet(Integer id, Credentials cred){
		Tweet tweet = tweetRepository.findByAuthorCredentialsAndIdAndDeletedAndAuthorDeleted(cred, id,false,false);
		if(tweet!=null){
			tweet.setDeleted(true);
			return tweet;
		}
		return null;
	}
}