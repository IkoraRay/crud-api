package com.onboarding.crud.api.services;

import com.onboarding.crud.api.dto.UserDto;
import com.onboarding.crud.api.dto.UserInsertDto;
import com.onboarding.crud.api.entities.User;
import com.onboarding.crud.api.exceptions.ResourceNotFoundException;
import com.onboarding.crud.api.exceptions.UsernameorEmailAlreadyUsedException;
import com.onboarding.crud.api.producers.KafkaMessageProducer;
import com.onboarding.crud.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;


	private final UserServiceGrpcClient userServiceGrpcClient =
			new UserServiceGrpcClient("localhost", 8081);

	@Cacheable(value = "user_info", key = "field")
	public UserDto findByUsernameOrEmail (String field) {
		User user = userRepository.findByUsernameOrEmail(field, field).orElseThrow(() -> new ResourceNotFoundException("User not found for username/email: " + field, field));
		return new UserDto(user);
	}

	public UserDto insertNewUser (UserInsertDto dto) {

		User user = new User(UUID.randomUUID().toString(), dto);
		userRepository.findByUsernameOrEmail(dto.username(), dto.username()).ifPresent(u -> {
			throw new UsernameorEmailAlreadyUsedException("Username already in use.", dto.username());
		});
		userRepository.findByUsernameOrEmail(dto.email(), dto.email()).ifPresent(u -> {
			throw new UsernameorEmailAlreadyUsedException("Email already in use.", dto.email());
		});
		user = userRepository.save(user);

		//kafkaMessageProducer.sendMessage(new StringBuilder().append("USER CREATED: ").append(user.getId()).toString(), "USER-ACTION");

		userServiceGrpcClient.sendAction("USER CREATED", user.getId());
		return new UserDto(user);
	}

	public UserDto updateUser (String id, UserInsertDto dto) {
		User userRequested = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for id:" + id, id));

		userRequested.setUsername(dto.username());
		userRequested.setEmail(dto.email());
		userRequested.setName(dto.name());
		userRequested.setPassword(dto.password());

		userRequested = userRepository.save(userRequested);

		//kafkaMessageProducer.sendMessage(new StringBuilder().append("USER UPDATED: ").append(userRequested.getId()).toString(), "USER-ACTION");

		userServiceGrpcClient.sendAction("USER UPDATED", userRequested.getId());

		return new UserDto(userRequested);

	}

	public void deleteUser (String value) {
		User userRequested = userRepository.findByUsernameOrEmail(value, value).orElseThrow(() -> new ResourceNotFoundException("User not found for Email/Username:" + value, value));
		userRepository.delete(userRequested);

		//kafkaMessageProducer.sendMessage(new StringBuilder().append("USER DELETED: ").append(userRequested.getId()).toString(), "USER-ACTION");

		userServiceGrpcClient.sendAction("USER DELETED", userRequested.getId());
	}

}

