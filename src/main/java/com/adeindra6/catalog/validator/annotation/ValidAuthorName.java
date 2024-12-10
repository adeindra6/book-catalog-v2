package com.adeindra6.catalog.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

import com.adeindra6.catalog.validator.AuthorNameValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = AuthorNameValidator.class)
public @interface ValidAuthorName {
    
    String message() default "author name invalid";

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
