package com.wordgame.generator.service;

import com.wordgame.generator.model.GeneratorResult;
import com.wordgame.management.service.GenerationPropertiesService;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Bratchikov
 */
@Service
@Order(2)
@RequiredArgsConstructor
@Getter
@Slf4j
public class InMemWordsBufferService {

    private final GeneratorService generatorService;
    private final GenerationPropertiesService generationPropertiesService;

    private final ConcurrentLinkedQueue<GeneratorResult> clq = new ConcurrentLinkedQueue<>();
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);

    @PostConstruct
    public void init() {
        while (clq.size() < generationPropertiesService.getBufferSize()) {
            addToBuffer();
        }
    }

    public void addToBuffer() {
        if (clq.size() < generationPropertiesService.getBufferSize()) {
            log.debug("Adding new result to buffer");
            clq.add(generatorService.generateWordSequence());
        }
    }

    public GeneratorResult getFromBuffer() {
        GeneratorResult generatorResult = clq.poll();
        executor.execute(this::addToBuffer);
        return generatorResult;
    }
}
