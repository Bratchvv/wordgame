package com.wordgame.admin.rfc;

import static java.lang.Thread.sleep;

import com.wordgame.generator.service.GeneratorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Специальный сервис для стрес теста, БЕЗ БУФЕРА, включается параметром rfc.raw.generator=true,
 * кол-во итераций - rfc.raw.count=999
 *
 * @author Vladimir Bratchikov
 */
@RequiredArgsConstructor
@Service
@Slf4j
@Order(3)
public class GeneratorRfc {

    private final GeneratorService generatorService;
    @Value("${rfc.raw.generator}")
    private boolean rfcEnabled;
    @Value("${rfc.raw.count}")
    private int count;

    @PostConstruct
    public void afterInit() {
        if (rfcEnabled) {
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
                    log.info("Avg time: {} ms, count: {}", timeSum.get() / countSum.get(), countSum.get());
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
