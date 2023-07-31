package com.example.zookeeperscheduler.validator;

import com.example.zookeeperscheduler.exception.DomainValidationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InputParameterValidator {

    public void validateParameters(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(e -> "@" + e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new DomainValidationException(errors);
        }
    }

    public void validateParameters(Set<ConstraintViolation<Object>> violations) {
        if (violations.size() > 0) {
            String errors = violations
                    .stream()
                    .map(e -> "@" + e.getPropertyPath() + ": " + e.getMessage())
                    .collect(Collectors.joining(", "));
            throw new DomainValidationException(errors);
        }
    }
}
