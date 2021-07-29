package com.wordgame.statistics.controller;

import com.wordgame.statistics.dto.ErrorDto;
import com.wordgame.statistics.dto.InputParamsDto;
import com.wordgame.statistics.dto.RatingTableDto;
import com.wordgame.statistics.service.RatingTablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/top/{id}/{name}")
    public ResponseEntity<?> getPlayerRatingsData(@PathVariable String id,
                                                  @PathVariable String name,
                                                  @RequestParam Integer upperSize,
                                                  @RequestParam Integer topSize,
                                                  @RequestParam Integer lowerSize) {
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

    @GetMapping("/player/{id}")
    public ResponseEntity<?> getPlayerRatingsData(@PathVariable String id, Pageable pageable) {
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

    @GetMapping("/player/{id}/{name}")
    public ResponseEntity<?> getPlayerRatingInTable(@PathVariable String id, @PathVariable String name) {
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
