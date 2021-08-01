package com.wordgame.management.controller;

import com.wordgame.management.dto.GameCategoriesDto;
import com.wordgame.management.service.GameCategoriesService;
import com.wordgame.statistics.dto.ErrorDto;
import java.time.LocalDateTime;
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

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody GameCategoriesDto categoriesDto) {
        try {
            return ResponseEntity.ok(gameCategoriesService.saveCategories(categoriesDto));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorDto.builder()
                    .name(e.getClass().getName())
                    .details(e.getMessage())
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @GetMapping("/check-version")
    public ResponseEntity<?> getActive(@RequestParam LocalDateTime versionDate) {
        try {
            if (gameCategoriesService.checkCategoriesByDate(versionDate)) {
                return ResponseEntity.ok("isActual: true");
            }
            return ResponseEntity.ok("isActual: false");
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
