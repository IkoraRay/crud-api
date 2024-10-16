package com.onboarding.crud.api.dto;

import com.onboarding.crud.api.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class UserDto {
	private static final long serialVersionUID = 1L;

	private final String id;
	private final String email;
	private final String name;
	private final String username;

	public UserDto (User user) {
		id = user.getId();
		email = user.getEmail();
		name = user.getName();
		username = user.getUsername();
	}
}
