package com.example.springverduleria.validation;

import com.example.springverduleria.model.Category;
import com.example.springverduleria.repository.CategoryRepository;
import com.example.springverduleria.service.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryIdValidator implements ConstraintValidator<ValidCategoryId, Long> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void initialize(ValidCategoryId constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long categoryId, ConstraintValidatorContext context) {
        if (categoryId == null) {
            return false;
        }
        return categoryRepository.findById(categoryId).isPresent();
    }
}