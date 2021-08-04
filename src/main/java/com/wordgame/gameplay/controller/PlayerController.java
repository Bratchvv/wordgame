package com.wordgame.gameplay.controller;

import com.wordgame.gameplay.dto.AdvancedInputPlayerDto;
import com.wordgame.gameplay.dto.AdvancedPlayerDto;
import com.wordgame.gameplay.dto.ErrorDto;
import com.wordgame.gameplay.dto.PlayerDto;
import com.wordgame.gameplay.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/player", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

    private final PlayerService playerService;

    @Operation(summary = "Get player data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get player data",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = AdvancedPlayerDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayerData(@PathVariable String id) {
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


    @Operation(summary = "Store new player")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "New player stored",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = PlayerDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
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

    @Operation(summary = "Update player data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player data updated",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = AdvancedPlayerDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @PutMapping
    public ResponseEntity<?> savePlayerData(@RequestBody AdvancedInputPlayerDto inputDto) {
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
