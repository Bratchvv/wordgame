package com.wordgame.management.controller;

import com.wordgame.management.dto.GameWordsInfoDto;
import com.wordgame.management.service.GameWordsService;
import com.wordgame.statistics.dto.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/words", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameWordsController {

    private final GameWordsService gameWordsService;

    @Operation(summary = "Get words files page")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get words files list",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GameWordsInfoDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping()
    public ResponseEntity<?> getWordsPage(Pageable pageable) {
        try {
            return ResponseEntity.ok(gameWordsService.getPages(pageable));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Get current active words file data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get active words",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GameWordsInfoDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/active")
    public ResponseEntity<?> getActive() {
        try {
            return ResponseEntity.ok(gameWordsService.getActiveWords());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
