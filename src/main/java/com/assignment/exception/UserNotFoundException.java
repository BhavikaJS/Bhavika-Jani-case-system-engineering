package com.assignment.exception;

/**
 * Exception if the user is not found in the database
 *
 */
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("User not found in the database- Please register before fetching or performing update/delete");
	}
}
