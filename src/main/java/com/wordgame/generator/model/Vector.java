package com.wordgame.generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Vladimir Bratchikov
 */
@Data
@AllArgsConstructor
public class Vector {

    private int x;
    private int y;

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
