package com.wordgame.admin.rfc;

import com.wordgame.generator.service.GeneratorService;
import com.wordgame.generator.service.InMemDictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

/**
 * @author Vladimir Bratchikov
 */
@RequiredArgsConstructor
@Service
@Slf4j
@Order(3)
public class GeneratorRfc {

    @Value("${rfc.raw.generator}")
    private boolean rfcEnabled;

    @Value("${rfc.raw.count}")
    private int count;

    private final GeneratorService generatorService;

    @PostConstruct
    public void afterInit() {
        if(rfcEnabled) {
            log.info("Run rfc test for raw generation ");
            AtomicLong timeSum = new AtomicLong(0);
            AtomicLong countSum = new AtomicLong(0);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
            for (int i = 0; i < count; i++) {
                executor.execute(() -> {
                    var time = System.currentTimeMillis();
                    generatorService.generateWordSequence();
                    timeSum.addAndGet(System.currentTimeMillis() - time);
                    countSum.incrementAndGet();
                    log.info("Avg time: {} ms, count: {}", timeSum.get()/countSum.get(), countSum.get());
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
