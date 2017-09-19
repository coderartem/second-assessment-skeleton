package com.cooksys.secondassessment.twitterapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.twitterapi.entity.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {

	Users findByUsername(String username);
}
