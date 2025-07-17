package com.rest_api_webservices.microservices.exceptions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.List;
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

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request
	) {
		List<String> errors = exception.getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.toList();

		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				"Validation failed",
				String.join("; ", errors)
		);

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	/*
	 * Handle validation errors thrown by @Valid annotation.
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid2(
			MethodArgumentNotValidException exception,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request
	) {
		String defaultMessage = exception.getFieldError() != null
				? exception.getFieldError().getDefaultMessage()
				: "Validation error";

		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				"Total Errors: " + exception.getErrorCount() + " | First Error: " + defaultMessage,
				request.getDescription(false)
		);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	protected ResponseEntity<Object> handleMethodArgumentNotValid2(
			MethodArgumentNotValidException exception,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request
	) {
		String errorMessage = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.findFirst()
				.orElse("Validation failed");

		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				errorMessage,
				request.getDescription(false)
		);

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	} */
}
