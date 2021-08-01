package com.wordgame.gameplay.controller;

import com.wordgame.generator.model.GeneratorResult;
import com.wordgame.generator.service.InMemWordsBufferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get generated result from buffer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get generated result from buffer",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GeneratorResult.class))})})
    @GetMapping
    public ResponseEntity<GeneratorResult> getTestResultBuffer() {
        return ResponseEntity.ok(inMemWordsBufferService.getFromBuffer());
    }
}
