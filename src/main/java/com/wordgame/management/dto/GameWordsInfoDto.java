package com.wordgame.management.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameWordsInfoDto {

    private Long id;
    private String name;
    private LocalDateTime date;
    private boolean active;
}
