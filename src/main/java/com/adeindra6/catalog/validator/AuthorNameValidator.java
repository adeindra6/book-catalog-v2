package com.adeindra6.catalog.validator;

import org.springframework.stereotype.Component;

import com.adeindra6.catalog.validator.annotation.ValidAuthorName;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class AuthorNameValidator implements ConstraintValidator<ValidAuthorName, String> {

    @Override
    public boolean isValid(String authorName, ConstraintValidatorContext context) {
        return !authorName.equalsIgnoreCase("Tes");
    }
    
}
