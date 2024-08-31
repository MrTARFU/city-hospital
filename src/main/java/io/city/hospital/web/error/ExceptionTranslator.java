package io.city.hospital.web.error;

import io.city.hospital.web.error.exception.PatientFoundException;
import io.city.hospital.web.error.model.ErrorResponseBuilder;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(PatientFoundException.class)
    public ResponseEntity<Object> handlePatientFoundException(HttpServletRequest request, PatientFoundException ex) {
        Span.current().recordException(ex).setStatus(StatusCode.ERROR);
        return new ErrorResponseBuilder()
                .originalRequest(request)
                .errorKey(Span.current().getSpanContext().getTraceId() + "-" + Span.current().getSpanContext().getSpanId())
                .title("Patient Not Found")
                .message(ex.getMessage())
                .buildResponseEntity(HttpStatus.NOT_FOUND);
    }
}
