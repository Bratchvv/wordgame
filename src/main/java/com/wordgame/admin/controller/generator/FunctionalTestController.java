package com.wordgame.admin.controller.generator;

import com.wordgame.generator.GeneratorService;
import com.wordgame.generator.model.GeneratorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/generator", produces = MediaType.APPLICATION_JSON_VALUE)
public class FunctionalTestController {

    private final GeneratorService generatorService;

    @GetMapping("/result/random")
    public ResponseEntity<GeneratorResult> getTestResult() {
        return ResponseEntity.ok(generatorService.generateWordSequence());
    }
}
