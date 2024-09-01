package io.city.hospital.aop;

import static io.city.hospital.AppConstants.X_ENVIRONMENT;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.city.hospital.web.error.exception.EnvironmentMismatchException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Aspect for Validating REST Requests Headers
 */
@Aspect
@Component
public class ValidateHeadersAspect {

    private final HttpServletRequest httpRequest;
    private final Environment environment;

    public ValidateHeadersAspect(HttpServletRequest httpRequest,
                                 Environment environment) {
        this.httpRequest = httpRequest;
        this.environment = environment;
    }

    @Pointcut("within(io.city.hospital.web.controller.*)")
    public void restController() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Validate before proceed with JoinPoint.
     */
    @Before("restController()")
    public void validate() {
        this.validateEnvironment(this.httpRequest.getHeader(X_ENVIRONMENT));
    }

    private void validateEnvironment(String env) {
        for (String profile : environment.getActiveProfiles()) {
            if (profile.equalsIgnoreCase(env)) {
                return;
            }
        }
        throw new EnvironmentMismatchException();
    }

}

