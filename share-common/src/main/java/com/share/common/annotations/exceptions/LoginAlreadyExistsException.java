package com.share.common.annotations.exceptions;

public class LoginAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -2240389749781807549L;
	
	public LoginAlreadyExistsException(final String exceptionMessage) {
		super(exceptionMessage);
	}

}
