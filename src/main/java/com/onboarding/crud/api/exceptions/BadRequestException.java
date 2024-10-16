package com.onboarding.crud.api.exceptions;

public class BadRequestException extends RuntimeException{

	public BadRequestException(String message){
		super(message);
	}
}
