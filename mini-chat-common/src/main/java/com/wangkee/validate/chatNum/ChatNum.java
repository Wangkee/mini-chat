package com.wangkee.validate.chatNum;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Constraint(validatedBy = ChatNumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChatNum {
    String message() default "Chat号格式不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}