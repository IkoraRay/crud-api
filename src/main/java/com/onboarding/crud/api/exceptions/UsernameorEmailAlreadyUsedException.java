package com.onboarding.crud.api.exceptions;

public class UsernameorEmailAlreadyUsedException extends RuntimeException {

	public final String field;
	public UsernameorEmailAlreadyUsedException (String message, String field) {
		super(message);
		this.field = field;
	}
}
