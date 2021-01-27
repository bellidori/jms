package com.example.demo;

import org.springframework.util.ErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * error handler.
 * @author Richard
 */
@Slf4j
public class DefaultErrorHandler implements ErrorHandler {
	@Override
	public void handleError(final Throwable t) {
		DefaultErrorHandler.log
				.error("problem occurred while processing the command", t);
	}
}
