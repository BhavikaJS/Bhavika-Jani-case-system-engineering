package com.assignment.exception;

/**
 * Exception if the credentials cannot be verified
 *
 */
public class WrongCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WrongCredentialsException() {
		super("UserName and Password doesn't match- Please check the password and try again");
	}
}
