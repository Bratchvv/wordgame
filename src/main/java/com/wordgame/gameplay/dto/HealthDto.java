package com.wordgame.gameplay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthDto extends HealthInputDto {

    private Long timeUTCSaving;
    private Long timeUTCNow;
}
