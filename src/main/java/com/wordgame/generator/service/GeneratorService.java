package com.wordgame.generator.service;

import com.wordgame.generator.algorithm.GeneratorAdapter;
import com.wordgame.generator.model.GeneratorInputParams;
import com.wordgame.generator.model.GeneratorResult;
import com.wordgame.management.service.GenerationPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Bratchikov
 */
@Service
@RequiredArgsConstructor
public class GeneratorService {

    private final InMemDictionaryService inMemDictionaryService;
    private final GenerationPropertiesService generationPropertiesService;

    public GeneratorResult generateWordSequence() {
        GeneratorAdapter adapter = new GeneratorAdapter();
        return adapter.generate(GeneratorInputParams.builder()
                                    .countRow(generationPropertiesService.getCountRowSize())
                                    .countColumn(generationPropertiesService.getCountColumnSize())
                                    .minCountWord(generationPropertiesService.getMinCountWordSize())
                                    .maxIteration(generationPropertiesService.getMaxIterationSize())
                                    .dictionaryWords(inMemDictionaryService.getInMemWordDictionary())
                                    .readFileWord(inMemDictionaryService.getInMemReadFileWord())
                                    .build());
    }
}
