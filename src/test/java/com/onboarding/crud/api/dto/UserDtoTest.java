package com.onboarding.crud.api.dto;

import com.onboarding.crud.api.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

	@Test
	public void testConstructor() {
		// Arrange
		User user = new User();
		user.setId("1");
		user.setEmail("test@example.com");
		user.setName("John Doe");
		user.setPassword("password");
		user.setUsername("johndoe");

		// Act
		UserDto userDto = new UserDto(user);

		// Assert
		Assertions.assertEquals("test@example.com", userDto.getEmail());
		Assertions.assertEquals("John Doe", userDto.getName());
		Assertions.assertEquals("johndoe", userDto.getUsername());
	}

	@Test
	public void testGettersAndSetters() {
		// Arrange
		UserDto userDto = UserDto.builder().id("2").email("test@example.com").name("John Doe").username("johndoe").build();

		// Act & Assert

		Assertions.assertEquals("test@example.com", userDto.getEmail());
		Assertions.assertEquals("John Doe", userDto.getName());
		Assertions.assertEquals("johndoe", userDto.getUsername());
	}
}
