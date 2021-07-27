package com.wordgame.management.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GamePropertyDto {

    private Long id;
    private String name;
    private String value;
    private String label;
}
