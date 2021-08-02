package com.wordgame.admin.model;

import lombok.Data;

@Data
public class StoreFilterForm {

    private String name;
    private String playerName;
    private String playerId;
    private Long id;
    private Long expireHoursCycle;
}
