package com.cooksys.secondassessment.twitterapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {

	Users findByUsernameAndDeleted(String username, Boolean deleted);
	Users findByUsername(String username);
	Users findByCredentials(Credentials cred);
}
