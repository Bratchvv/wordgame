package com.wordgame.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
