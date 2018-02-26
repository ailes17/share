package com.share.view.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.share.common.validations.EmailValidator;
import com.share.security.login.Login;
import com.share.view.constants.PageNames;
import com.share.view.utils.Util;
 
@Named
@SessionScoped
public class LoginBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String password;
    private String uname;
    
    @Inject
    private Login login;
    @Inject
    private EmailValidator emailValidator;
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getUname() {
        return uname;
    }
 
    public void setUname(String uname) {
        this.uname = uname;
    }
    
 
    public String loginProject() {
    	if (!emailValidator.validateEmail(uname)) {
    		FacesContext.getCurrentInstance().addMessage("loginForm:email", new FacesMessage("Invalid Email format"));
            return null;
    	}
    	
        boolean result = login.login(uname, password);
        if (result) {

            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);
            return PageNames.INDEX_PAGE + PageNames.FACES_REDIRECT;
        } else {
            FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage("Invalid Login"));
            return null;
        }
    }
    
    public boolean isConnected() {
    	return (Util.getSession() != null && Util.getSession().getAttribute("username") != null) ? true : false;
    }
 
    public String logout() {
      HttpSession session = Util.getSession();
      session.invalidate();
      return PageNames.LOGIN_PAGE + PageNames.FACES_REDIRECT;
   }
}