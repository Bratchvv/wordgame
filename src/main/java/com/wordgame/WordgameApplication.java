package com.wordgame;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

/**
 * @author Vladimir Bratchikov
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
public class WordgameApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordgameApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterInit() {
        log.info("LOAD WORDGAME SERVER APP V - {}", "1.0");
    }

    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }
}
