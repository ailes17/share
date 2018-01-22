package com.share.security.login;

import com.share.common.annotations.exceptions.DatabaseException;
import com.share.common.annotations.exceptions.LoginAlreadyExistsException;

public interface NewUser {

	void addNewUser(String username, String password, String firstName, String lastName) throws DatabaseException, LoginAlreadyExistsException;
}
