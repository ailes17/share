package com.share.common.annotations.exceptions;

public class DatabaseException extends Exception {

	private static final long serialVersionUID = -2240389749781807549L;
	
	public DatabaseException(final String exceptionMessage) {
		super(exceptionMessage);
	}

}
