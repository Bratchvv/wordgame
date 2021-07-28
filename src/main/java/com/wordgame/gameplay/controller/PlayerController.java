package com.wordgame.gameplay.controller;

import com.wordgame.gameplay.dto.AdvancedPlayerDto;
import com.wordgame.gameplay.dto.ErrorDto;
import com.wordgame.gameplay.dto.PlayerDto;
import com.wordgame.gameplay.entity.Player;
import com.wordgame.gameplay.repository.PlayerRepository;
import com.wordgame.gameplay.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/player", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayerData(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(playerService.getPlayerData(id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody PlayerDto inputDto) {
        try {
            return ResponseEntity.ok(playerService.createPlayer(inputDto));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> savePlayerData(@RequestBody AdvancedPlayerDto inputDto) {
        try {
            return ResponseEntity.ok(playerService.savePlayerData(inputDto));
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
