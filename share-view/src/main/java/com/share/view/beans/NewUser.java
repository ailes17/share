package com.share.view.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
 
@Named
@SessionScoped
public class NewUser implements Serializable {
 
    private static final long serialVersionUID = 1L;
    
    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;
    
    
    
 
    public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String addUser() {
		return "";
	}
	
	public String reset() {
		this.emailAddress = null;
		this.password = null;
		this.firstName = null;
		this.lastName = null;
		return "";
	}
 

}