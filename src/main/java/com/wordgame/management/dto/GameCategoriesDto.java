package com.wordgame.management.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class GameCategoriesDto {

    private Long id;

    private LocalDateTime date;

    @NotBlank
    private GameCategoriesData data;

    private boolean active;
}
