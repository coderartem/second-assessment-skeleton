package com.cooksys.secondassessment.twitterapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.twitterapi.entity.Credentials;
import com.cooksys.secondassessment.twitterapi.entity.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {

	List<Users> findByDeleted(Boolean deleted);
	Users findByUsernameAndDeleted(String username, Boolean deleted);
	Users findByUsername(String username);
	Users findByCredentials(Credentials cred);
	Users findByCredentialsAndDeleted(Credentials cred, Boolean deleted);
	List<Users> findByUsernameAndFollowingDeleted (String username,Boolean deleted);
	List<Users> findByDeletedAndLikedId(Boolean deleted, Integer id);  //Works good
	Users findByUsernameAndFollowersCredentials(String username, Credentials cred);
//	List<Users> findByUsernameAndDeletedAndFollowingDeleted(String username, Boolean deleted, Boolean followDeleted);
//	List<Users> findByUsernameAndDeletedAndFollowersDeleted(String username, boolean b, boolean c);
	List<Users> findByUsernameInAndDeleted(List<String> usernames, Boolean deleted);
}
