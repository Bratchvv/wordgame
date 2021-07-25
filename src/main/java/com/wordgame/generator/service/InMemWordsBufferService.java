package com.wordgame.generator.service;

import com.wordgame.admin.service.GenerationPropertiesService;
import com.wordgame.generator.model.GeneratorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

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
        if(clq.size() < generationPropertiesService.getBufferSize()) {
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
