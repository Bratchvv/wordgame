package com.wordgame.gameplay.dto;

import lombok.*;

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
