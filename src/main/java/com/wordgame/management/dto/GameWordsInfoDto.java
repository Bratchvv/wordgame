package com.wordgame.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameWordsInfoDto {

    private Long id;
    private String name;
    private LocalDateTime date;
    private Integer category;
    private boolean active;
}
