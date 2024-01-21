package com.qrestor.auth.api.exception;

import com.qrestor.commons.errors.dto.ApiErrorResponseDTO;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAppExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ApiErrorResponseDTO handleStudentNotFoundException(ValidationException exception, HttpRequest request) {
        return new ApiErrorResponseDTO(exception.getMessage(), "Custom Validation error", request.getURI().getPath());
    }
}
