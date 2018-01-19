package com.share.view.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.share.security.login.Login;
import com.share.view.utils.Util;
 
@Named
@SessionScoped
public class LoginBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String password;
    private String uname;
    
    @Inject
    private Login login;
 
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
        boolean result = login.login(uname, password);
        if (result) {

            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);            
            return "/filtered/index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage("Invalid Login"));
            
             // invalidate session, and redirect to other pages
             //message = "Invalid Login. Please Try Again!";
            return null;
        }
    }
    
    public boolean isConnected() {
    	return (Util.getSession() != null && Util.getSession().getAttribute("username") != null) ? true : false;
    }
 
    public String logout() {
      HttpSession session = Util.getSession();
      session.invalidate();
      return "/login.xhtml?faces-redirect=true";
   }
}