package com.share.view.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.share.common.annotations.exceptions.DatabaseException;
import com.share.common.annotations.exceptions.LoginAlreadyExistsException;
import com.share.common.validations.EmailValidator;
import com.share.security.login.NewUser;
import com.share.view.constants.PageNames;
import com.share.view.utils.Util;
 
@Named
@SessionScoped
public class NewUserBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    
    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;
    
    
    @Inject
    private NewUser newUser;
    @Inject
    private EmailValidator emailValidator;
 
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
		String result = PageNames.INDEX_PAGE + PageNames.FACES_REDIRECT;
		
		if (!emailValidator.validateEmail(emailAddress)) {
    		FacesContext.getCurrentInstance().addMessage("newUserForm:newUserEmail", new FacesMessage("Invalid Email format"));
            return null;
    	}
		
		try {
			newUser.addNewUser(emailAddress, password, firstName, lastName);
			HttpSession session = Util.getSession();
            session.setAttribute("username", emailAddress);
		} catch (DatabaseException e) {
			FacesContext.getCurrentInstance().addMessage("newUserForm", new FacesMessage(e.getMessage()));
			this.password = null;
			result = null;
		} catch (LoginAlreadyExistsException e) {
			FacesContext.getCurrentInstance().addMessage("newUserForm:newUserEmail", new FacesMessage(e.getMessage()));
			this.password = null;
			result = null;
		}
		return result;
	}
	
	public String reset() {
		this.emailAddress = null;
		this.password = null;
		this.firstName = null;
		this.lastName = null;
		return "";
	}
 

}