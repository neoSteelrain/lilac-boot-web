package com.steelrain.springboot.lilac.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

import java.util.Optional;

public class ValidationErrorException extends LilacException {

    @Getter
    private Optional<Errors> errors;

    @Getter
    private Object request;

    public ValidationErrorException(Errors errors, Object request) {
        super(errors.getObjectName());
        this.errors = Optional.ofNullable(errors);
        this.request = request;
    }
}
