package com.cooksys.secondassessment.twitterapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.twitterapi.service.UserService;
import com.cooksys.secondassessment.twitterapi.service.ValidateService;

@RestController
@RequestMapping("validate")
public class ValidateController {

	

	private ValidateService vS;

	public ValidateController(ValidateService vS) {
		this.vS = vS;
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean hasUser(@PathVariable String username){
		return vS.hasUser(username);
	}
	
	@GetMapping("username/available/@{username}")
	public boolean nameAvailable(@PathVariable String username){
		return vS.nameAvailable(username);
	}
	
	@GetMapping("tag/exists/{label}")
	public boolean tagExists(@PathVariable String label){
		return vS.tagExists(label);
	}

}
