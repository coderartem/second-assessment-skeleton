package com.cooksys.secondassessment.twitterapi.cfactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.cooksys.secondassessment.twitterapi.entity.Hashtag;
import com.cooksys.secondassessment.twitterapi.entity.Mention;
import com.cooksys.secondassessment.twitterapi.entity.Tweet;
import com.cooksys.secondassessment.twitterapi.input.dto.TweetInput;
import com.cooksys.secondassessment.twitterapi.repository.HashTagRepository;
import com.cooksys.secondassessment.twitterapi.repository.MentionRepsoitory;
import com.cooksys.secondassessment.twitterapi.repository.TweetRepository;
import com.cooksys.secondassessment.twitterapi.repository.UserRepository;


@Component
public class TweetFactory {
	
	private UserRepository uR;
	private TweetRepository tR;
	private HashTagRepository hR;
	private MentionRepsoitory mR;
	
	public TweetFactory( UserRepository uR, TweetRepository tR, HashTagRepository hR, MentionRepsoitory mR) {
		this.uR = uR;
		this.tR = tR;
		this.hR = hR;
		this.mR = mR;
	}
	
	public Tweet createTweet(TweetInput tweetIn){
			Tweet tweet = new Tweet();
			
			if(uR.findByCredentialsAndDeleted(tweetIn.getCredentials(), false)==null) return null;
			
			tweet.setAuthor(uR.findByCredentials(tweetIn.getCredentials())); 
			tweet.setPosted(new Timestamp(System.currentTimeMillis()).getTime());
			tweet.setContent(tweetIn.getContent());
			tweet.setDeleted(false);
			
			Matcher hashTag = Pattern.compile("#(\\w+)").matcher(tweetIn.getContent());
			List<Hashtag> hL = new ArrayList<>();
			while(hashTag.find()){
				Hashtag hstg = hR.findByLabel(hashTag.group());
				if(hstg!=null){
					hstg.setLastUsed(new Timestamp(System.currentTimeMillis()).getTime());
					hL.add(hstg);
				}else{
					
					Hashtag hshtg = new Hashtag();
					hshtg.setLabel(hashTag.group());
					hshtg.setLastUsed(hshtg.getFirstUsed());
					hR.saveAndFlush(hshtg);
					hL.add(hshtg);
				}
			}
			tweet.setHashtag(hL);
			
			List<Mention> mL = new ArrayList<>();
			Matcher mention = Pattern.compile("@(\\w+)").matcher(tweetIn.getContent());
			while(mention.find()){
				
				Mention mn = mR.findByMention(mention.group());
				if(mn!=null){
					mL.add(mn);
				}else{
					Mention men = new Mention();
					men.setMention(mention.group());
					mR.saveAndFlush(men);
					mL.add(men);
				}
			}
			
			tweet.setMentions(mL);
			tR.saveAndFlush(tweet);
			
		return tweet;
	}

}
