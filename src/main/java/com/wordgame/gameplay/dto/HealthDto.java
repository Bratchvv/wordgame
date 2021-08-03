package com.wordgame.gameplay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthDto {

    private Integer lifes;
    private Long secondsRestoreLife;
    private Long timeUTCSaving;
    private Long timeUTCNow;
}
