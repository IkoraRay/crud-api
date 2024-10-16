package com.onboarding.crud.api.services;

import com.onboarding.crud.api.dto.UserDto;
import com.onboarding.crud.api.dto.UserInsertDto;
import com.onboarding.crud.api.entities.User;
import com.onboarding.crud.api.exceptions.ResourceNotFoundException;
import com.onboarding.crud.api.exceptions.UsernameorEmailAlreadyUsedException;
import com.onboarding.crud.api.producers.KafkaMessageProducer;
import com.onboarding.crud.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private KafkaMessageProducer kafkaMessageProducer;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	public void setUp () {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void givenValidInput_whenSearchingUserByUsername_thenReturnUserData () {
		User user1 = new User(
				"id1",
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		UserDto validator = new UserDto(user1);

		when(userRepository.findByUsernameOrEmail("johndoe", "johndoe")).thenReturn(Optional.of(user1));

		UserDto dto = userService.findByUsernameOrEmail("johndoe");

		assertEquals(dto, validator);
	}

	@Test
	public void givenInvalidInput_whenSearchingUserByUsername_thenThrowNewException () {


		when(userRepository.findByUsernameOrEmail("janedoe", "janedoe")).thenReturn(Optional.empty());


		assertThrows(ResourceNotFoundException.class, () -> userService.findByUsernameOrEmail("janedoe"));
	}

	@Test
	public void givenValidInput_whenSearchingUserByEmail_thenReturnUserData () {
		User user1 = new User(
				"id1",
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		UserDto validator = new UserDto(user1);

		when(userRepository.findByUsernameOrEmail("test@test.com", "test@test.com")).thenReturn(Optional.of(user1));

		UserDto dto = userService.findByUsernameOrEmail("test@test.com");

		assertEquals(dto, validator);
	}

	@Test
	public void givenInvalidInput_whenSearchingUserByEmail_thenThrowNewException () {


		when(userRepository.findByUsernameOrEmail("test2@test.com", "test2@test.com")).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.findByUsernameOrEmail("test2@test.com"));
	}

	@Test
	public void givenValidInput_whenInsertingNewUser_thenReturnDto () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		User user1 = new User(
				"id1",
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		when(userRepository.save(any())).thenReturn(user1);

		UserDto result = userService.insertNewUser(insertDto);

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

		User user1 = new User(
				"id1",
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		when(userRepository.findByUsernameOrEmail(insertDto.username(), insertDto.username())).thenReturn(Optional.of(user1));

		assertThrows(UsernameorEmailAlreadyUsedException.class, () -> userService.insertNewUser(insertDto));
	}

	@Test
	public void givenAlreadyUsedEmail_whenInsertNewUser_thenThrowNewException () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		User user1 = new User(
				"id1",
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		when(userRepository.findByUsernameOrEmail(insertDto.email(), insertDto.email())).thenReturn(Optional.of(user1));

		assertThrows(UsernameorEmailAlreadyUsedException.class, () -> userService.insertNewUser(insertDto));
	}

	@Test
	public void givenValidInput_whenUpdatingUser_thenReturnDto () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		User user1 = new User(
				"id1",
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");
		when(userRepository.findById("id1")).thenReturn(Optional.of(user1));

		when(userRepository.save(user1)).thenReturn(user1);

		UserDto result = userService.updateUser("id1", insertDto);

		assertNotNull(result);
		assertEquals(insertDto.email(), result.getEmail());
		assertEquals(insertDto.name(), result.getName());
		assertEquals(insertDto.username(), result.getUsername());
	}

	@Test
	public void givenInvalidInput_whenUpdatingUser_theThrowNewException () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");


		when(userRepository.findById("NoUsedId")).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.updateUser("NoUsedId", insertDto));

	}

	@Test
	public void givenValidInput_whenDeletingUser_thenReturnOK () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		User user1 = new User(
				"id1",
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		when(userRepository.findByUsernameOrEmail(insertDto.username(), insertDto.username())).thenReturn(Optional.of(user1));

		userService.deleteUser(insertDto.username());

	}

	@Test
	public void givenInvalidInput_whenDeletingUser_thenThrowNewException () {
		UserInsertDto insertDto = new UserInsertDto(
				"test@test.com",
				"John Doe",
				"password",
				"johndoes");

		User user1 = new User(
				"id1",
				"test@test.com",
				"John Doe",
				"password",
				"johndoe");

		when(userRepository.findByUsernameOrEmail(insertDto.username(), insertDto.username())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(insertDto.username()));

	}


}
