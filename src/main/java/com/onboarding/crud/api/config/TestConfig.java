package com.onboarding.crud.api.config;

import com.onboarding.crud.api.entities.User;
import com.onboarding.crud.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static java.util.UUID.randomUUID;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig implements CommandLineRunner {

	private UserRepository userRepository;

	@Autowired
	public TestConfig (UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run (String... args) throws Exception {

		User user = new User(randomUUID().toString(), "test@test.com",
				"Tester Test Tested", "12345", "test1");
		User user2 = new User(randomUUID().toString(), "test2@test.com",
				"Tester Test Tested", "12345", "test2");
		userRepository.save(user);
		userRepository.save(user2);
	}
}
