package com.cooksys.secondassessment.twitterapi.converter;

import java.sql.Timestamp;
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
public class TweetCreator {
	
	private UserRepository uR;
	private TweetRepository tR;
	private HashTagRepository hR;
	private MentionRepsoitory mR;
	
	public TweetCreator( UserRepository uR, TweetRepository tR, HashTagRepository hR, MentionRepsoitory mR) {
		this.uR = uR;
		this.tR = tR;
		this.hR = hR;
		this.mR = mR;
	}
	
	public Tweet createTweet(TweetInput tweetIn){
			Tweet tweet = new Tweet();
			
			tweet.setAuthor(uR.findByCredentials(tweetIn.getCredentials()));  //Null check needed
			tweet.setPosted(new Timestamp(System.currentTimeMillis()).getTime());
			tweet.setContent(tweetIn.getContent());
			tweet.setDeleted(false);
			
			Matcher hashTag = Pattern.compile("#(\\w+)").matcher(tweetIn.getContent());
			while(hashTag.find()){
				
				Hashtag hstg = hR.findByLabel(hashTag.toString());
				if(hstg!=null){
					tweet.getHashtag().add(hstg);
				}else{
					Hashtag hshtg = new Hashtag(hashTag.toString());
					hR.saveAndFlush(hshtg);
					tweet.getHashtag().add(hshtg);		//HZ dolzhen li ya vmesto etogo dobavit' cherez poisk v DB?
				}
			}
			Matcher mention = Pattern.compile("@(\\w+)").matcher(tweetIn.getContent());
			while(mention.find()){
				
				Mention mn = mR.findByMention(mention.toString());
				if(mn!=null){
					tweet.getMentions().add(mn);
				}else{
					Mention men = new Mention();
					men.setMention(mention.toString());
					mR.saveAndFlush(men);
					tweet.getMentions().add(men);
				}
			}
		
		return tweet;
	}

}
