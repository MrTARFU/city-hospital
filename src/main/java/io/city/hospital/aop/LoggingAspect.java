package io.city.hospital.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * Aspect for Logging MVC Controller Methods and Exceptions
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper;
    public LoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Pointcut("within(io.city.hospital.web.controller.*)")
    public void restController() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     */
    @Around("restController()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if(LOGGER.isInfoEnabled()) {
            var jsonRequest = this.objectMapper.writeValueAsString(joinPoint.getArgs());
            LOGGER.info("[Enter]: {}.{}() with argument[s] = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    jsonRequest
            );
        }
        var result = joinPoint.proceed();
        if(LOGGER.isInfoEnabled()) {
            var jsonResponse = this.objectMapper.writeValueAsString(result);
            LOGGER.info("[Exit]: {}.{}() with result = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    jsonResponse
            );
        }
        return result;
    }
    /**
     * Logs the result of every method
     */
    @AfterReturning(
            pointcut = "execution(*io.city.hospital.web.error.ExceptionTranslator.*(..))",
            returning = "result"
    )
    public void logAfterErrorResponse(JoinPoint pointcut, Object result) throws JsonProcessingException {
        var jsonValue = this.objectMapper.writeValueAsString(result);
        LOGGER.error("[Error] handler result = {}", jsonValue);
    }
}
