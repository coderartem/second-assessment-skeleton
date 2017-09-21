package com.cooksys.secondassessment.twitterapi.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hashtag {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String label;
	private final Long firstUsed = new Timestamp(System.currentTimeMillis()).getTime();
	private Long lastUsed;
	
	public Hashtag() {
	}
	
	public Hashtag(String label) {
		this.label = label;
		this.lastUsed = firstUsed;
	}
	
	public String getLabel() {
		return label;
	}
	public Long getFirstUsed() {
		return firstUsed;
	}
	public Long getLastUsed() {
		return lastUsed;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setLastUsed(Long lastUsed) {
		this.lastUsed = lastUsed;
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
		Hashtag other = (Hashtag) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
