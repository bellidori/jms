package com.example.demo;

/**
 * error when processing command.
 * @author Richard
 */
public class CommandProcessingException extends RuntimeException {
	private static final long serialVersionUID = -818937816732482325L;

	/**
	 * create an exception from a another one.
	 * @param message error message
	 * @param cause error cause
	 */
	CommandProcessingException(final String message, final Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * create an exception.
	 * @param message error message
	 */
	CommandProcessingException(final String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
