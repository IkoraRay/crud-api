package com.onboarding.crud.api.log;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;


@Data
@RequiredArgsConstructor
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

	@Autowired
	LoggingService loggingService;

	HttpServletRequest httpServletRequest;

	@Override
	public boolean supports(MethodParameter methodParameter, Type type,
	                        Class<? extends HttpMessageConverter<?>> aClass) {
		return true;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
	                            MethodParameter parameter, Type targetType,
	                            Class<? extends HttpMessageConverter<?>> converterType) {

		loggingService.logRequest(httpServletRequest, body);

		return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
	}
}
