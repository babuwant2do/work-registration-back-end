package com.wordpress.babuwant2do.workregistration.web.rest.vm;

import java.io.Serializable;

public class LoginVM implements Serializable{
	private String username;
    private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
