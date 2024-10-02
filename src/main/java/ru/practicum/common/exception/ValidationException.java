package ru.practicum.common.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class ValidationException extends RuntimeException {

    private final BindingResult bindingResult;

    public ValidationException(final BindingResult bindingResult) {
        super("Validation error");
        this.bindingResult = bindingResult;
    }
}
