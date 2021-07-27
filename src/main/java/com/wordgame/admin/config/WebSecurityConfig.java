package com.wordgame.admin.config;

import com.wordgame.management.service.EmployeeDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final EmployeeDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // Описываем бин для шивровальщика паролей
        return new BCryptPasswordEncoder();
    }

    /**
     * Бин глобальных настроек Spring Security
     * @param auth - билдер для установки параметров AuthenticationManager
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    /**
     * Настройки безопастности.
     *
     * @param http контекст
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // отключаем CORS (если https - нужно включить)
        http.authorizeRequests().antMatchers("/api/v1/**", "/login", "/logout").permitAll();
        http.authorizeRequests().antMatchers("/", "/management/**").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Стандартная настройка логин формы
        http.authorizeRequests().and().formLogin()//
            .loginProcessingUrl("/j_spring_security_check") // Стандартный URL spring security
            .loginPage("/login")
            .defaultSuccessUrl("/management/rating", true)
            .failureUrl("/login?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            // Настройка выхода
            .and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessUrl("/login")
            .and().rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400);
    }

}