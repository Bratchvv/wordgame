package com.wordgame.generator.service;

import com.wordgame.generator.algorithm.GeneratorAdapter;
import com.wordgame.generator.model.GeneratorInputParams;
import com.wordgame.generator.model.GeneratorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Bratchikov
 */
@Service
@RequiredArgsConstructor
public class GeneratorService {

    private final InMemDictionaryService inMemDictionaryService;

    public GeneratorResult generateWordSequence() {
        GeneratorAdapter adapter = new GeneratorAdapter();
        return adapter.generate(GeneratorInputParams.builder()
                .countRow(5)
                .countColumn(5)
                .dictionaryWords(inMemDictionaryService.getInMemWordDictionary())
                .readFileWord(inMemDictionaryService.getInMemReadFileWord())
                .build());
    }
}
