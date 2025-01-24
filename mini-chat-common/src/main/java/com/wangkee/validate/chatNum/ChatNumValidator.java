package com.wangkee.validate.chatNum;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChatNumValidator implements ConstraintValidator<ChatNum, String> {

    // 校验方法，返回 true 为校验通过，false 为校验失败
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // 如果是空值，考虑到可能有 @NotNull 注解，空值会被其它注解处理
        }

        // 正则表达式：允许6到20个英文、数字和符号的组合
        String regex = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{}|;:'\",.<>?/]*$";

        return value.matches(regex) && value.length() >= 6 && value.length() <= 20;
    }
}