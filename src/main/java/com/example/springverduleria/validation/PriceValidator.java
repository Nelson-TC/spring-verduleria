package com.example.springverduleria.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<ValidPrice, Double> {

    @Override
    public void initialize(ValidPrice constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String valueStr = String.valueOf(value);
        int decimalSeparatorIndex = valueStr.indexOf(".");
        if (decimalSeparatorIndex != -1 && valueStr.length() - decimalSeparatorIndex > 3) {
            return false;
        }

        try {
            Double.parseDouble(valueStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
