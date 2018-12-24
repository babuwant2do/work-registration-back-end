package com.wordpress.babuwant2do.workregistration.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/api/users")
public class UserResource {

	@GetMapping("/message")
	public String getMessage(){
		return "Hello User";
	}
}
