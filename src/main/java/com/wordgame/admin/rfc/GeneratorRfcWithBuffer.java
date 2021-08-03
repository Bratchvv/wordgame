package com.wordgame.admin.rfc;

import static java.lang.Thread.sleep;

import com.wordgame.generator.service.InMemWordsBufferService;
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
 * Специальный сервис для стрес теста, С БУФЕРОМ, включается параметром rfc.buffered.generator=true,
 * кол-во итераций - rfc.buffered.count=999
 *
 * @author Vladimir Bratchikov
 */
@RequiredArgsConstructor
@Service
@Slf4j
@Order(3)
public class GeneratorRfcWithBuffer {

    private final InMemWordsBufferService inMemWordsBufferService;
    @Value("${rfc.buffered.count}")
    private int count;

    @PostConstruct
    public void afterInit() {
        if (count>0) {
            log.info("Run rfc test for buffered generation");
            AtomicLong timeSum = new AtomicLong(0);
            AtomicLong countSum = new AtomicLong(0);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
            for (int i = 0; i < count; i++) {
                executor.execute(() -> {
                    var time = System.currentTimeMillis();
                    inMemWordsBufferService.getFromBuffer();
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
