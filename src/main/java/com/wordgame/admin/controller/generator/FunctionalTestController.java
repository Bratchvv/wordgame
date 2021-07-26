package com.wordgame.admin.controller.generator;

import com.wordgame.generator.service.GeneratorService;
import com.wordgame.generator.model.GeneratorResult;
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
@RequestMapping(value = "/api/v1/test/generator", produces = MediaType.APPLICATION_JSON_VALUE)
public class FunctionalTestController {

    private final GeneratorService generatorService;
    private final InMemWordsBufferService inMemWordsBufferService;

    @GetMapping("/result/new")
    public ResponseEntity<GeneratorResult> getTestResultNew() {
        return ResponseEntity.ok(generatorService.generateWordSequence());
    }

    @GetMapping("/result/buffer")
    public ResponseEntity<GeneratorResult> getTestResultBuffer() {
        return ResponseEntity.ok(inMemWordsBufferService.getFromBuffer());
    }
}
