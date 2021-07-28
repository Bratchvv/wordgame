package com.wordgame.gameplay.controller;

import com.wordgame.generator.model.GeneratorResult;
import com.wordgame.generator.service.GeneratorService;
import com.wordgame.generator.service.InMemWordsBufferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vladimir Bratchikov
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/generator", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeneratorController {

    private final InMemWordsBufferService inMemWordsBufferService;

    @GetMapping
    public ResponseEntity<GeneratorResult> getTestResultBuffer() {
        return ResponseEntity.ok(inMemWordsBufferService.getFromBuffer());
    }
}
