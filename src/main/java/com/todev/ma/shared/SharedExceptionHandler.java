package com.todev.ma.shared;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
class SharedExceptionHandler {

    @ExceptionHandler(value = DuplicatedDataException.class)
    @ResponseStatus(value = CONFLICT)
    ErrorResponseData handle(DuplicatedDataException exception) {
        return new ErrorResponseData(exception.getMessage(), Set.of());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = BAD_REQUEST)
    ErrorResponseData handle(MethodArgumentNotValidException exception) {
        String message = "Os dados recebidos estão ausentes ou se encontram em um formato inválido";
        Set<String> details = new LinkedHashSet<>();

        for (ObjectError validationError : exception.getAllErrors()) {
            details.add(validationError.getDefaultMessage());
        }

        return new ErrorResponseData(message, details);
    }

    @ExceptionHandler(value = SecurityException.class)
    @ResponseStatus(value = UNAUTHORIZED)
    ErrorResponseData handle(SecurityException exception) {
        return new ErrorResponseData(exception.getMessage(), Set.of());
    }

    @ExceptionHandler(value = BusinessRuleViolationException.class)
    @ResponseStatus(value = BAD_REQUEST)
    ErrorResponseData handle(BusinessRuleViolationException exception) {
        return new ErrorResponseData(exception.getMessage(), Set.of());
    }
}
