package com.onboarding.crud.api.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

	@Test
	public void testConstructorAndGettersSetters() {
		// Arrange
		String id = "1";
		String email = "test@example.com";
		String name = "John Doe";
		String password = "password";
		String username = "johndoe";

		// Act
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setUsername(username);

		// Assert
		Assertions.assertEquals(id, user.getId());
		Assertions.assertEquals(email, user.getEmail());
		Assertions.assertEquals(name, user.getName());
		Assertions.assertEquals(password, user.getPassword());
		Assertions.assertEquals(username, user.getUsername());
	}
}
