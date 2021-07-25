package com.wordgame.admin.controller.rfc;

import com.wordgame.generator.GeneratorService;
import com.wordgame.generator.InMemDictionaryService;
import com.wordgame.generator.model.GeneratorResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

@RequiredArgsConstructor
@Service
@Slf4j
public class GeneratorRfc {

    @Value("${rfc.generator}")
    private boolean rfcEnabled;

    @Value("${rfc.count}")
    private int count;

    private final GeneratorService generatorService;
    private final InMemDictionaryService inMemDictionaryService;

    @EventListener(ApplicationReadyEvent.class)
    public void afterInit() {
        if(rfcEnabled) {
            log.info("Запуск нагрузочного теста интеграции");
            AtomicLong timeSum = new AtomicLong(0);
            AtomicLong countSum = new AtomicLong(0);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
            for (int i = 0; i < count; i++) {
                executor.execute(() -> {
                    if(inMemDictionaryService.getInMemWordDictionary().isEmpty()) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    var time = System.currentTimeMillis();
                    generatorService.generateWordSequence();
                    timeSum.addAndGet(System.currentTimeMillis() - time);
                    countSum.incrementAndGet();
                    log.info("Среднее время выполнения генерации: {} мс", timeSum.get()/countSum.get());
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

            }
            executor.shutdown();
        }
    }
}
