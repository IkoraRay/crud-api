package com.onboarding.crud.api.log;

import com.onboarding.crud.api.log.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Data
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

	@Autowired
	LoggingService loggingService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	                         Object handler) {

		if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
				&& request.getMethod().equals(HttpMethod.GET.name())) {
			loggingService.logRequest(request, null);
		}

		return true;
	}
}

