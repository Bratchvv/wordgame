package com.wordgame.management.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GameCategoriesDto {

    private Long id;

    private String name;

    private LocalDateTime date;

    private GameCategoriesData data;

    private boolean active;
}
