package com.giftservice.springboot.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Documented
@Constraint(validatedBy = FromMDCValidator.class)
public @interface FromMDC {
  String message() default "Tracker ID or UserId is missing";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}


