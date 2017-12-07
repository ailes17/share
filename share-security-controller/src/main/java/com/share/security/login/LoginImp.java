package com.share.security.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.share.model.dao.LoginDAO;

@Named
@SessionScoped
public class LoginImp implements Login, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6883392579160731958L;
	
	@Inject
	private LoginDAO loginDao;
	
	@Override
	public boolean login(String username, String password) {
		return loginDao.login(username, password);
	}

}
