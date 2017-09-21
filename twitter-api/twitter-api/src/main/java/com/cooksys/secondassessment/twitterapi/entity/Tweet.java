package com.cooksys.secondassessment.twitterapi.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Tweet {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Users author;
	private Long posted;
	private String content;
	
	private boolean deleted;
	
	@ManyToMany
	private List<Users> likedBy;  //Dvuhstoronka, hz esli budet rabotat', mozhno ot odnoj storony izbavitsya
	
	@OneToMany   
	private List<Tweet> replies;
	
	@ManyToOne
	private Tweet inReplyTo;
	
	@ManyToOne
	private Tweet repostOf;
	
	@ManyToMany
	private List<Hashtag> hashtag;
	
	@ManyToMany
	private List<Mention> mentions;

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Users getAuthor() {
		return author;
	}
	public void setAuthor(Users author) {
		this.author = author;
	}
	public Long getPosted() {
		return posted;
	}
	public void setPosted(Long posted) {
		this.posted = posted;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public List<Users> getLikedBy() {
		return likedBy;
	}
	public void setLikedBy(List<Users> likedBy) {
		this.likedBy = likedBy;
	}
	public List<Tweet> getReplies() {
		return replies;
	}
	public void setReplies(List<Tweet> replies) {
		this.replies = replies;
	}
	public Tweet getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public Tweet getRepostOf() {
		return repostOf;
	}
	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}
	public List<Hashtag> getHashtag() {
		return hashtag;
	}
	public void setHashtag(List<Hashtag> hashtag) {
		this.hashtag = hashtag;
	}
	public List<Mention> getMentions() {
		return mentions;
	}
	public void setMentions(List<Mention> mentions) {
		this.mentions = mentions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
