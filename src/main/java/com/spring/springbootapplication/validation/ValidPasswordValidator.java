package com.spring.springbootapplication.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {}

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // パスワードが空の場合は @NotEmpty に任せる（ここではエラーを発生させない）
        if (password == null || password.isEmpty()) {
            return true;
        }
        // 英数字8文字以上のバリデーションをチェック
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
}
