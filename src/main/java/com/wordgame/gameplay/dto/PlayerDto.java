package com.wordgame.gameplay.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

    private Long id;
    private String name;
    private String urlAvatar;

}
