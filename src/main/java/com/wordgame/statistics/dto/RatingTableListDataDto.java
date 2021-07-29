package com.wordgame.statistics.dto;

import com.wordgame.gameplay.dto.PlayerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingTableListDataDto {
    private Integer value;
    private PlayerDto player;
}
