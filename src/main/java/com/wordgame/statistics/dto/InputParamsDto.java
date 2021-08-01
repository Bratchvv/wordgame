package com.wordgame.statistics.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputParamsDto {
    @NotBlank
    private String playerId;
    @NotBlank
    private String name;
    @NotBlank
    private Integer value;
    private Integer expireDayCount;
}
