package com.wordgame.gameplay.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

    private String id;
    private String name;
    private String urlAvatar;

}
