package com.onboarding.crud.api.resources;

import com.onboarding.crud.api.config.MetricsHelper;
import com.onboarding.crud.api.dto.UserDto;
import com.onboarding.crud.api.dto.UserInsertDto;
import com.onboarding.crud.api.exceptions.BadRequestException;
import com.onboarding.crud.api.exceptions.ResourceNotFoundException;
import com.onboarding.crud.api.exceptions.UsernameorEmailAlreadyUsedException;
import com.onboarding.crud.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserResourceTest {

	@Mock
	private UserService userService;

	@Mock
	private MetricsHelper metricsHelper;

	@InjectMocks
	private UserResource userResource;

	@BeforeEach
	public void setUp () {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void givenValidInput_whenSearchingUserByUsername_thenReturnUserData () {
		UserDto dto = new UserDto("id1",
				"test@test.com",
				"John Doe",
				"johndoe");
		when(userService.findByUsernameOrEmail("johndoe")).thenReturn(dto);

		UserDto result = userResource.findByUsernameOrEmail("johndoe", "").getBody();

		assertNotNull(result);
		assertEquals(dto.getEmail(), result.getEmail());
		assertEquals(dto.getName(), result.getName());
		assertEquals(dto.getUsername(), result.getUsername());

	}

	@Test
	public void givenInvalidInput_whenSearchingUserByUsername_thenThrowNewException () {

		when(userService.findByUsernameOrEmail("janedoe")).thenThrow(ResourceNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () ->
				userResource.findByUsernameOrEmail("janedoe", "").getBody());

	}

	@Test
	public void givenValidInput_whenSearchingUserByEmail_thenReturnUserData () {
		UserDto dto = new UserDto("id1",
				"test@test.com",
				"John Doe",
				"johndoe");
		when(userService.findByUsernameOrEmail("test@test.com")).thenReturn(dto);

		UserDto result = userResource.findByUsernameOrEmail("", "test@test.com").getBody();

		assertNotNull(result);
		assertEquals(dto.getEmail(), result.getEmail());
		assertEquals(dto.getName(), result.getName());
		assertEquals(dto.getUsername(), result.getUsername());

	}

	@Test
	public void givenInvalidInput_whenSearchingUserByEmail_thenThrowNewException () {

		when(userService.findByUsernameOrEmail("test2@test.com")).thenThrow(ResourceNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () ->
				userResource.findByUsernameOrEmail("", "test2@test.com").getBody());

	}

	@Test
	public void givenInvalidInput_whenSearching_thenThrowNewException () {
		assertThrows(BadRequestException.class, () -> userResource.findByUsernameOrEmail("", ""));
	}


	@Test
	public void givenValidInput_whenInsertingNewUser_thenReturnUserData () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		UserDto dto = new UserDto("id1",
				"test@test.com",
				"John Doe",
				"johndoe");

		when(userService.insertNewUser(insertDto)).thenReturn(dto);

		UserDto result = userResource.insertNewUser(insertDto).getBody();

		assertNotNull(result);
		assertEquals(insertDto.email(), result.getEmail());
		assertEquals(insertDto.name(), result.getName());
		assertEquals(insertDto.username(), result.getUsername());
	}

	@Test
	public void givenAlreadyUsedUsername_whenInsertNewUser_thenThrowNewException () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");


		when(userService.insertNewUser(insertDto)).thenThrow(UsernameorEmailAlreadyUsedException.class);

		assertThrows(UsernameorEmailAlreadyUsedException.class, () -> userResource.insertNewUser(insertDto));
	}

	@Test
	public void givenAlreadyUsedEmail_whenInsertNewUser_thenThrowNewException () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");


		when(userService.insertNewUser(insertDto)).thenThrow(UsernameorEmailAlreadyUsedException.class);

		assertThrows(UsernameorEmailAlreadyUsedException.class, () -> userResource.insertNewUser(insertDto));
	}

	@Test
	public void givenValidInput_whenUpdatingUser_thenReturnUserData () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"Johnathan Doe",
				"password",
				"johndoe");

		UserDto dto = new UserDto("id1",
				"test@test.com",
				"Johnathan Doe",
				"johndoe");

		String id = "id1";

		when(userService.updateUser(id, insertDto)).thenReturn(dto);

		UserDto result = userResource.updateUser(insertDto, id).getBody();

		assertEquals(insertDto.email(), result.getEmail());
		assertEquals(insertDto.name(), result.getName());
		assertEquals(insertDto.username(), result.getUsername());
	}

	@Test
	public void givenInvalidId_whenUpdatingUser_thenThrowException () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"Johnathan Doe",
				"password",
				"johndoe");

		when(userService.updateUser("id2", insertDto)).thenThrow(ResourceNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> userResource.updateUser(insertDto, "id2"));
	}

	@Test
	public void givenValidInput_whenDeletingByUsername_thenReturnOk () {
		userResource.deleteUser("johndoe", "");
	}

	@Test
	public void givenValidInput_whenDeletingByEmail_thenReturnOk () {
		userResource.deleteUser("", "test@test.com");
	}

	@Test
	public void givenInvalidInput_whenDeleting_thenThrowNewException () {
		assertThrows(BadRequestException.class, () -> userResource.deleteUser("", ""));
	}
}
