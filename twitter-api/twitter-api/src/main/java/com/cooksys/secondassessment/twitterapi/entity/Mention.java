package com.cooksys.secondassessment.twitterapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Mention {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String mention;
	
	
	public Integer getId() {
		return id;
	}
	public String getMention() {
		return mention;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMention(String mention) {
		this.mention = mention;
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
		Mention other = (Mention) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
