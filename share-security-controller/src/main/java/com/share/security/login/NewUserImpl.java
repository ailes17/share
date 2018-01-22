package com.share.security.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.share.common.annotations.exceptions.DatabaseException;
import com.share.common.annotations.exceptions.LoginAlreadyExistsException;
import com.share.model.dao.NewUserDAO;


@Named
@SessionScoped
class NewUserImpl implements NewUser, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6883392579160731958L;
	
	@Inject
	private NewUserDAO newUserDao;
	
	@Override
	public void addNewUser(final String username, final String password, final String firstName, final String lastName) throws DatabaseException, LoginAlreadyExistsException {
		newUserDao.addNewUser(username, password, firstName, lastName);
	}

}
