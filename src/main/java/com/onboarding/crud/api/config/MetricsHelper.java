package com.onboarding.crud.api.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class MetricsHelper {

	private final MeterRegistry registry;

	public MetricsHelper(final MeterRegistry registry) {
		this.registry = registry;
	}


	public void registryGetLapsedTime (Long time, String field) {
		Timer.builder("crud.application.getuser.time")
				.tags("User Info", field)
				.publishPercentileHistogram()
				.register(registry)
				.record(Duration.ofMillis(time));
	}

	public void incrementUpdateRequest (final String field) {
		registry.counter("crud.application.updateuser.counter", "User Info", field).increment();
	}

	public void incrementPostRequest (final String field) {
		registry.counter("crud.application.registeruser.counter", "User Info", field).increment();
	}

	public void incrementDeleteRequest (final String field) {
		registry.counter("crud.application.deleteuser.counter", "User Info", field).increment();
	}

	public void incrementExceptionThrows (final String field, final String exception) {
		registry.counter("crud.application.exceptions.counter",
				"User Info", field, "Exception", exception).increment();
	}
}
