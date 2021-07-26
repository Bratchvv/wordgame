package com.wordgame.admin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Vladimir Bbratchikov
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final Session session;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor(session));
    }

}
