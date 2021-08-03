package com.wordgame.management.controller;

import com.wordgame.management.dto.GameCategoriesData;
import com.wordgame.management.dto.GameCategoriesDto;
import com.wordgame.management.service.GameCategoriesService;
import com.wordgame.statistics.dto.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameCategoriesController {

    private final GameCategoriesService gameCategoriesService;

    @Operation(summary = "Get categories data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get categories data",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GameCategoriesDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategories(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(gameCategoriesService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                                            .name(e.getClass().getName())
                                            .details(e.getMessage())
                                            .build(),
                                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Save categories data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories data saved",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GameCategoriesDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody GameCategoriesData categories) {
        try {
            return ResponseEntity.ok(gameCategoriesService.saveCategories(categories));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                                            .name(e.getClass().getName())
                                            .details(e.getMessage())
                                            .build(),
                                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get rating tables list")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get page",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GameCategoriesDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping()
    public ResponseEntity<?> getTableRatingsData(Pageable pageable) {
        try {
            return ResponseEntity.ok(gameCategoriesService.getPages(pageable));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                                            .name(e.getClass().getName())
                                            .details(e.getMessage())
                                            .build(),
                                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Check local categories version with active on server")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "isActual: true,"
            + " when local version is same with sever, false, when different",
            content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/check-version")
    public ResponseEntity<?> getActive(@RequestParam @NotBlank Long id) {
        try {
            if (gameCategoriesService.checkCategoriesByDate(id)) {
                return ResponseEntity.ok("{\"isActual\": true}");
            }
            return ResponseEntity.ok("{\"isActual\": false}");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                                            .name(e.getClass().getName())
                                            .details(e.getMessage())
                                            .build(),
                                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get current active categories file data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get active categories",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GameCategoriesDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/active")
    public ResponseEntity<?> getActive() {
        try {
            return ResponseEntity.ok(gameCategoriesService.getActive());
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
