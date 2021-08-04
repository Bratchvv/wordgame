package com.wordgame.gameplay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthInputDto {

    private Integer lifes;
    private Long secondsRestoreLife;
}
