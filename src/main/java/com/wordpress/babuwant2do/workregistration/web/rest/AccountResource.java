package com.wordpress.babuwant2do.workregistration.web.rest;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordpress.babuwant2do.workregistration.domain.User;
import com.wordpress.babuwant2do.workregistration.repository.UserRepository;
import com.wordpress.babuwant2do.workregistration.service.UserService;
import com.wordpress.babuwant2do.workregistration.web.rest.vm.ManagedUserVM;

@RestController
@RequestMapping("/api")
public class AccountResource {
	
	private final UserRepository userRepository;
	private final UserService userService;
	
	public AccountResource(UserRepository userRepository, UserService userService){
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
	
	//TODO: handle Exception GLOBAL
	@PostMapping(path = "/register",
	        produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
		HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        
		return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
	            .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
	            .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
	                .map(user -> new ResponseEntity<>("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
	                .orElseGet(() -> {
	                    User user = userService
	                        .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
	                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
	                            managedUserVM.getEmail().toLowerCase());

	                    return new ResponseEntity(HttpStatus.CREATED);
	                })
	        );
	}
	
	

}
