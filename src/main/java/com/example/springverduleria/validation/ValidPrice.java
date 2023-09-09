package com.example.springverduleria.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPrice {
    String message() default "El valor no es un número válido o contiene más de dos decimales.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
