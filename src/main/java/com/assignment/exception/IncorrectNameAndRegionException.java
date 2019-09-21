package com.assignment.exception;

/**
 * Exception if the name and region doesn't match from that in the database
 *
 */
public class IncorrectNameAndRegionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IncorrectNameAndRegionException() {
		super("Invalid access- The UserName and Region combination is not valid");
	}
}
