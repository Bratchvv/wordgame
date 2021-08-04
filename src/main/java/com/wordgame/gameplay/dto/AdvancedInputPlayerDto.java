package com.wordgame.gameplay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdvancedInputPlayerDto extends PlayerDto {

    private HealthInputDto health;

    public AdvancedInputPlayerDto(String id, String name, String urlAvatar, HealthDto health) {
        super(id, name, urlAvatar);
        this.health = health;
    }
}
