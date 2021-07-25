package com.wordgame.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class GeneratorConfiguration {

    private final InMemDictionaryService inMemDictionaryService;

    @EventListener(ApplicationReadyEvent.class)
    public void afterInit() {
        log.info("Запуск механизма загрузки словаря, при инициализации приложения");
        var time = System.currentTimeMillis();
        inMemDictionaryService.fullUpdateFromFile();
        log.info("Словарь успешно загружен. Размер словаря: {}. Время загрузки: {}мс",
                inMemDictionaryService.getInMemWordDictionary().size(),
                (System.currentTimeMillis() - time));
    }

}
