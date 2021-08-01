package com.wordgame.gameplay.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String urlAvatar;

}
