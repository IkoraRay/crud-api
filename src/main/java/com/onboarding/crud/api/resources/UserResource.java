package com.onboarding.crud.api.resources;

import com.onboarding.crud.api.config.MetricsHelper;
import com.onboarding.crud.api.dto.UserDto;
import com.onboarding.crud.api.dto.UserInsertDto;
import com.onboarding.crud.api.exceptions.BadRequestException;
import com.onboarding.crud.api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

	private final UserService userService;

	private final MetricsHelper metricsHelper;

	@GetMapping
	public ResponseEntity<UserDto> findByUsernameOrEmail (
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String email
	) {

		StopWatch watch = new StopWatch();

		if (ObjectUtils.isNotEmpty(username)) {
			watch.start();
			UserDto userDto = userService.findByUsernameOrEmail(username);
			watch.stop();
			metricsHelper.registryGetLapsedTime(watch.getTotalTimeMillis(), username);
			return ResponseEntity.ok().body(userDto);
		} else if (ObjectUtils.isNotEmpty(email)) {
			watch.start();
			UserDto userDto = userService.findByUsernameOrEmail(email);
			watch.stop();
			metricsHelper.registryGetLapsedTime(watch.getTotalTimeMillis(), email);
			return ResponseEntity.ok().body(userDto);
		} else {
			throw new BadRequestException("Either 'username' or 'email' needed.");
		}

	}

	@PostMapping
	public ResponseEntity<UserDto> insertNewUser (@Valid @RequestBody UserInsertDto dto) {
		UserDto userDto = userService.insertNewUser(dto);
		metricsHelper.incrementPostRequest(dto.username());
		return ResponseEntity.ok(userDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser (
			@Valid @RequestBody UserInsertDto dto, @PathVariable String id
	) {
		UserDto userDto = userService.updateUser(id, dto);
		metricsHelper.incrementUpdateRequest(id);
		return ResponseEntity.ok(userDto);
	}

	@DeleteMapping
	public ResponseEntity<String> deleteUser (@RequestParam("username") String username, @RequestParam("email") String email) {

		if (ObjectUtils.isNotEmpty(username)) {
			userService.deleteUser(username);
			metricsHelper.incrementDeleteRequest(username);
		} else if (ObjectUtils.isNotEmpty(email)) {
			userService.deleteUser(email);
			metricsHelper.incrementDeleteRequest(username);
		} else {
			throw new BadRequestException("Either 'username' or 'email' needed.");
		}

		return ResponseEntity.ok().body("User deleted.");
	}

}
