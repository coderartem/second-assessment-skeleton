package com.cooksys.secondassessment.twitterapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {

	List<Users> findByDeleted(Boolean deleted);
	Users findByUsernameAndDeleted(String username, Boolean deleted);
	Users findByUsername(String username);
	Users findByCredentialsAndDeleted(Credentials cred, Boolean deleted);
	List<Users> findByDeletedAndLikedId(Boolean deleted, Integer id);
	Users findByUsernameAndFollowersCredentials(String username, Credentials cred);
	List<Users> findByUsernameInAndDeleted(List<String> usernames, Boolean deleted);
}
