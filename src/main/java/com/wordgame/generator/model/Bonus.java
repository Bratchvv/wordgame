package com.wordgame.generator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Vladimir Bratchikov
 */
@Data
@AllArgsConstructor
@Builder
public class Bonus {

    private Vector vector;
    private BonusVariant bonusVariant;

}
