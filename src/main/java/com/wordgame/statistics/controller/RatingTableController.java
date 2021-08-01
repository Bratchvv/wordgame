package com.wordgame.statistics.controller;

import com.wordgame.statistics.dto.EditableRatingTableDto;
import com.wordgame.statistics.dto.ErrorDto;
import com.wordgame.statistics.dto.InputParamsDto;
import com.wordgame.statistics.dto.RatingPlayerListDataDto;
import com.wordgame.statistics.dto.RatingTableDto;
import com.wordgame.statistics.dto.RatingTopDto;
import com.wordgame.statistics.service.RatingTablesService;
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
@RequestMapping(value = "/api/v1/rating", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingTableController {

    private final RatingTablesService ratingTablesService;

    //Получение рейтинга.
    // Передаваемые параметры:
    // id игрока,
    // название таблицы,
    // кол-во записей выше игрока,
    // кол-во записей ниже игрока,
    // и кол-во результатов топа.
    @Operation(summary = "Get player ratings top")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User global rating",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = RatingTopDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/top/{id}/{name}")
    public ResponseEntity<?> getPlayerRatingsData(@PathVariable @NotBlank String id,
                                                  @PathVariable @NotBlank String name,
                                                  @RequestParam @NotBlank Integer upperSize,
                                                  @RequestParam @NotBlank Integer topSize,
                                                  @RequestParam @NotBlank Integer lowerSize) {
        try {
            return ResponseEntity.ok(
                ratingTablesService.getPlayerRatingsData(id, name, upperSize, topSize, lowerSize)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get player ratings list")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User ratings",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = RatingPlayerListDataDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/player/{id}")
    public ResponseEntity<?> getPlayerRatingsData(@PathVariable  @NotBlank String id, Pageable pageable) {
        try {
            return ResponseEntity.ok(ratingTablesService.getRatingsByPlayer(id, pageable));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get player rating from specific table")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User rating",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = RatingTableDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/player/{id}/{name}")
    public ResponseEntity<?> getPlayerRatingInTable(@PathVariable @NotBlank String id,
                                                    @PathVariable @NotBlank String name) {
        try {
            return ResponseEntity.ok(ratingTablesService.getPlayerRatingInTable(id, name));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Save player data to rating table")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User rating stored to table",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EditableRatingTableDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @PostMapping("/player")
    public ResponseEntity<?> insertRatingData(@RequestBody InputParamsDto inputDto) {
        try {
            return ResponseEntity.ok(ratingTablesService.insertDataToTable(inputDto.getName(),
                    inputDto.getPlayerId(), inputDto.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get rating table by name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rating table by name",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = RatingTableDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/table/{name}")
    public ResponseEntity<?> getTableRatingsData(@PathVariable String name, Pageable pageable) {
        try {
            return ResponseEntity.ok(ratingTablesService.getRatingsByTable(name, pageable));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get rating tables page")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of rating tables",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = RatingTableDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @GetMapping("/table")
    public ResponseEntity<?> getTableRatingsList(Pageable pageable) {
        try {
            return ResponseEntity.ok(ratingTablesService.getRatingsTableList(pageable));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new rating table")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Created new rating table, "
            + "or inform about existing rating table",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EditableRatingTableDto.class))}),
        @ApiResponse(responseCode = "500", description = "Server error, see server logs",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class))})})
    @PostMapping("/table")
    public ResponseEntity<?> createRatingTable(@RequestBody InputParamsDto inputDto) {
        try {
            return ResponseEntity.ok(
                ratingTablesService.createRatingTable(inputDto.getName(), inputDto.getExpireDayCount())
            );
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
