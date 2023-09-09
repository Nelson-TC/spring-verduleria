package com.example.springverduleria.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategoryId {
    String message() default "El categoryId no es válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
