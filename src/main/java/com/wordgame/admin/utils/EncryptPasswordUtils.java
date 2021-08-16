package com.wordgame.admin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptPasswordUtils {

    /**
     * Специальная утилита для генерации BCrypt шифрованного пароля.
     *
     * @param password имходный пароль
     * @return зашифрованный пароль
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "admin";
        String encrytedPassword = encryptPassword(password);
        log.info("Encryted Password: " + encrytedPassword);
    }

}