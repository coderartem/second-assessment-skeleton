package com.cooksys.secondassessment.twitterapi.entity;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Users {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String username;
	@Embedded
	private Profile profile;
	@Embedded
	private Credentials credentials;
	private Long joined;
	
	private boolean deleted;
	
	@ManyToMany
	private List<Users> followers;
	
	@ManyToMany
	private List<Users> following;
	
	@ManyToMany
	private List<Tweet> liked;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Long getJoined() {
		return joined;
	}
	public void setJoined(Long joined) {
		this.joined = joined;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public List<Users> getFollowers() {
		return followers;
	}
	public void setFollowers(List<Users> followers) {
		this.followers = followers;
	}
	public List<Users> getFollowing() {
		return following;
	}
	public void setFollowing(List<Users> following) {
		this.following = following;
	}
	public List<Tweet> getLiked() {
		return liked;
	}
	public void setLiked(List<Tweet> liked) {
		this.liked = liked;
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
		Users other = (Users) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
