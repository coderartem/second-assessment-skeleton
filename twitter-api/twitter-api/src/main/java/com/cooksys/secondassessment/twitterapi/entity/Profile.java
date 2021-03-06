package com.cooksys.secondassessment.twitterapi.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Artem Kolin
 * 
 * Profile Dto for UsersCreationData, also embedded into Users Entity as user Profile
 *
 */
@Embeddable
public class Profile {
	
	private String firstName;
	private String lastName;
	@NotNull
	private String email;
	private String phone;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
