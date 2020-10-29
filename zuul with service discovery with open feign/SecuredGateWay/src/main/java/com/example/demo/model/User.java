package com.example.demo.model;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class User{
    
	@Id
    private String email;
   
    private String password;
   
    private String name;
   
   
    private String address;
    
   
    private List<String> roles;
    
  
	public User() {
		this.roles=new ArrayList<>();
		
	}


	public User( String email, String password, String name, String address, List<String> roles) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.roles = roles;
	}


	


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	


	


}
