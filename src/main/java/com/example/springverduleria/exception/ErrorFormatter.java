package com.example.springverduleria.exception;

import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorFormatter {

    public static Map<String, Object> formatValidationErrors(List<FieldError> fieldErrors) {
        Map<String, List<String>> errors = fieldErrors.stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        Map<String, Object> response = new HashMap<>();
        response.put("errors", errors);

        return response;
    }
}
