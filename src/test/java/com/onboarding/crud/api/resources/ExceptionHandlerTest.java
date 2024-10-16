package com.onboarding.crud.api.resources;

import com.onboarding.crud.api.config.MetricsHelper;
import com.onboarding.crud.api.exceptions.BadRequestException;
import com.onboarding.crud.api.exceptions.ResourceNotFoundException;
import com.onboarding.crud.api.exceptions.UsernameorEmailAlreadyUsedException;
import com.onboarding.crud.api.resources.exceptions.ResourceExceptionHandler;
import com.onboarding.crud.api.resources.exceptions.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlerTest {

    private ResourceExceptionHandler resourceExceptionHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private MetricsHelper metricsHelper;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        resourceExceptionHandler = new ResourceExceptionHandler(metricsHelper);
    }

    @Test
    public void testEntityNotFound(){
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found.", "entitynotfound");
        ResponseEntity<StandardError> response = resourceExceptionHandler.resolveEntityNotFound(exception, request);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Resource not found.", response.getBody().error());
    }

    @Test
    public void testFieldAlreadyUse(){
        UsernameorEmailAlreadyUsedException exception =
                new UsernameorEmailAlreadyUsedException("Username/Email already in use.", "usedfield");
        ResponseEntity<StandardError> response = resourceExceptionHandler.resolveUsernameEmailAlreadyUsed(exception, request);
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertEquals("Username/Email already in use.", response.getBody().error());
    }

    @Test
    public void testBadRequest(){
        BadRequestException exception = new BadRequestException("Please provide either 'username' or 'email'.");
        ResponseEntity<StandardError> response = resourceExceptionHandler.resolveBadRequestMissingField(exception, request);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Please provide either 'username' or 'email'.", response.getBody().error());
    }

}
