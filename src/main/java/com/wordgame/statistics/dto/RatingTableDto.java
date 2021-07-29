package com.wordgame.statistics.dto;

import java.time.LocalDate;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingTableDto {
    private String name;
    private LocalDate startDate;
    private Integer expireDayCount;
}
