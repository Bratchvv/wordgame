package com.wordgame.management.dto;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameWordsDto {

    private Long id;
    private String name;
    private LocalDateTime date;
    private Integer category;
    private String data;
    private boolean active;
}
