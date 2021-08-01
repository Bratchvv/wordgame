package com.wordgame.management.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameWordsDto {

    private Long id;
    private String name;
    private LocalDateTime date;
    private String data;
    private boolean active;
}
