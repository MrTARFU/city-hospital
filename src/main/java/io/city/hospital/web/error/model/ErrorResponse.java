package io.city.hospital.web.error.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.city.hospital.AppConstants;

public class ErrorResponse {

    private String errorKey;
    private String title;
    private String message;
    private Integer status;
    private String path;


    @JsonProperty(value = AppConstants.X_ENVIRONMENT)
    private String environment;

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }
}
