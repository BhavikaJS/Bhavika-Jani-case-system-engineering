package com.assignment.exception;

/**
 * Exception if the old password is same as the new password
 */
public class ChangePasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ChangePasswordException() {
		super("New Passwod cannot be same as old Password");
	}
}
