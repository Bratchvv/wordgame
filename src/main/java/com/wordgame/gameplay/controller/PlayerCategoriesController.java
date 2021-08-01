package com.wordgame.gameplay.controller;

import com.wordgame.gameplay.dto.ErrorDto;
import com.wordgame.gameplay.dto.PlayerGameCategoriesData;
import com.wordgame.gameplay.service.PlayerGameCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/player/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerCategoriesController {

    private final PlayerGameCategoriesService playerGameCategoriesService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayerData(@PathVariable String id) {
        try {
            return ResponseEntity.ok(playerGameCategoriesService.getData(id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createPlayer(@PathVariable String id,
                                          @RequestBody PlayerGameCategoriesData inputDto) {
        try {
            playerGameCategoriesService.saveData(id, inputDto);
            return ResponseEntity.ok(id);
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
