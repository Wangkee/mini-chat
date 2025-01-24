package com.wangkee.validate.nickname;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknameValidator implements ConstraintValidator<Nickname, String> {

    // 校验方法，返回 true 为校验通过，false 为校验失败
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !value.isEmpty() && value.length() <= 10;
    }
}