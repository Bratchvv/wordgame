package com.wordgame.management.controller;

import com.wordgame.management.service.GameWordsService;
import com.wordgame.statistics.dto.ErrorDto;
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

    @GetMapping()
    public ResponseEntity<?> getTableRatingsData(Pageable pageable) {
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

    @GetMapping("/active")
    public ResponseEntity<?> getActive() {
        try {
            return ResponseEntity.ok(gameWordsService.getActiveWords(0));
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
