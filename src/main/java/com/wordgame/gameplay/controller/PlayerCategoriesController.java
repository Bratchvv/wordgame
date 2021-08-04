package com.wordgame.gameplay.controller;

import com.wordgame.gameplay.dto.ErrorDto;
import com.wordgame.gameplay.dto.PlayerGameCategoriesData;
import com.wordgame.gameplay.service.PlayerGameCategoriesService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Сохранение и получение прогресса в карточках
 *
 * @author vbratchikov
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/player/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerCategoriesController {

    private final PlayerGameCategoriesService playerGameCategoriesService;

    @Operation(summary = "Get player categories data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get player categories data",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = PlayerGameCategoriesData.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
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

    @Operation(summary = "Store new player categories data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "New player categories data stored",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = String.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @PostMapping("/{id}")
    public ResponseEntity<?> createPlayerCategories(@PathVariable String id,
                                          @RequestBody PlayerGameCategoriesData inputDto) {
        try {
            playerGameCategoriesService.saveData(id, inputDto);
            return ResponseEntity.ok("{\"id\" : \"" + id + "\"}");
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
