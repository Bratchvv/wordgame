package com.wordgame.statistics.filter;

import lombok.Data;

/**
 * @author Vladimir Bratchikov
 */
@Data
public class RatingTableFilterForm  {

    private String name;
    private Long expireHoursCycle;
}
