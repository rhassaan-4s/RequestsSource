package com._4s_.common.web.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE} ) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface Unique {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String property() default "id";

    Class<?> value();

}