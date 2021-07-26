package com.wordgame.admin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // приватный конструктор для класса-утилиты
public class WebUtils {

    /**
     * Метод утилита, для текстового описания ролей пользователя.
     *
     * @param user  пользователь из контекста Spring
     * @return текстовое описания пользователя
     */
    public static String toString(User user) {
        StringBuilder sb = new StringBuilder();

        sb.append("UserName:").append(user.getUsername());

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            sb.append(" (");
            boolean first = true;
            for (GrantedAuthority a : authorities) {
                if (first) {
                    sb.append(a.getAuthority());
                    first = false;
                } else {
                    sb.append(", ").append(a.getAuthority());
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }

}