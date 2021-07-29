package com.wordgame.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputParamsDto {
    private String playerId;
    private String name;
    private Integer value;
    private Integer expireDayCount;
}
