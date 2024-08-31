package io.city.hospital.web.error.model;

import io.city.hospital.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class ErrorResponseBuilder {

    private final ErrorResponse instance;

    public ErrorResponseBuilder() {
        this.instance = new ErrorResponse();
    }

    public ErrorResponseBuilder errorKey(String errorKey) {
        this.instance.setErrorKey(errorKey);
        return this;
    }

    public ErrorResponseBuilder title(String title) {
        this.instance.setTitle(title);
        return this;
    }

    public ErrorResponseBuilder message(String message) {
        this.instance.setMessage(message);
        return this;
    }

    public ErrorResponseBuilder originalRequest(HttpServletRequest request) {
        this.instance.setEnvironment(request.getHeader(AppConstants.X_ENVIRONMENT));
        this.instance.setPath(request.getServletPath());
        return this;
    }

    public ErrorResponseBuilder originalRequest(WebRequest request) {
        return this.originalRequest(((ServletWebRequest) request).getRequest());
    }

    public ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus) {
        this.instance.setStatus(httpStatus.value());
        return new ResponseEntity<>(this.instance, httpStatus);
    }

}
