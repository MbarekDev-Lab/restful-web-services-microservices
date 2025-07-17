package com.rest_api_webservices.microservices.exception;

import com.rest_api_webservices.microservices.exceptions.ErrorDetails;
import com.rest_api_webservices.microservices.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler to return custom error responses for the API.
 */
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * Handle all unhandled exceptions (fallback).
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				ex.getMessage(),
				request.getDescription(false)
		);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * Handle UserNotFoundException specifically.
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				ex.getMessage(),
				request.getDescription(false)
		);

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
}
