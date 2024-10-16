package com.onboarding.crud.api.resources.exceptions;

import com.onboarding.crud.api.config.MetricsHelper;
import com.onboarding.crud.api.exceptions.BadRequestException;
import com.onboarding.crud.api.exceptions.ResourceNotFoundException;
import com.onboarding.crud.api.exceptions.UsernameorEmailAlreadyUsedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
@RequiredArgsConstructor
public class ResourceExceptionHandler {


	private final MetricsHelper metricsHelper;


	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resolveEntityNotFound (ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(
				Instant.now(),
				status.value(),
				"Resource not found.",
				e.getMessage(),
				request.getRequestURI()
		);
		metricsHelper.incrementExceptionThrows(e.field, "NOT_FOUND");
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(UsernameorEmailAlreadyUsedException.class)
	public ResponseEntity<StandardError> resolveUsernameEmailAlreadyUsed (UsernameorEmailAlreadyUsedException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(
				Instant.now(),
				status.value(),
				"Username/Email already in use.",
				e.getMessage(),
				request.getRequestURI()
		);
		metricsHelper.incrementExceptionThrows(e.field, "CONFLICT");
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<StandardError> resolveBadRequestMissingField (BadRequestException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(
				Instant.now(),
				status.value(),
				"Please provide either 'username' or 'email'.",
				e.getMessage(),
				request.getRequestURI()
		);
		metricsHelper.incrementExceptionThrows("NULL", "BAD_REQUEST");
		return ResponseEntity.status(status).body(err);
	}
}
