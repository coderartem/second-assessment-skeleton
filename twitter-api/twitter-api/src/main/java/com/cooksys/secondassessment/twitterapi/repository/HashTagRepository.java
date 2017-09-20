package com.cooksys.secondassessment.twitterapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.twitterapi.entity.Hashtag;

public interface HashTagRepository extends JpaRepository<Hashtag,Integer> {

	Hashtag findByLabel(String hashtag);
}
