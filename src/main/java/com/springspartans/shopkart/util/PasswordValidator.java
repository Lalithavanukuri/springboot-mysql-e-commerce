package com.springspartans.shopkart.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";
    public boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.matches(PASSWORD_PATTERN);
    }
}

/**
     *  Rules of validation of the password:
     * - Must be between 8 to 15 characters long.
     * - Contain at least one lowercase letter.
     * - Contain at least one uppercase letter.
     * - Contain at least one numeric digit.
     * - Contain at least one special character (@, $, !, %, *, ?, &).
     */