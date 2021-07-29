package com.wordgame.gameplay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdvancedPlayerDto extends PlayerDto {

    private HealthDto health;

    public AdvancedPlayerDto(String id, String name, String urlAvatar, HealthDto health) {
        super(id, name, urlAvatar);
        this.health = health;
    }
}
