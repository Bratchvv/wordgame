package com.wordgame.generator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class Vector2Int {

    static final Vector2Int Zero = new Vector2Int(0, 0);
    static final Vector2Int Up = new Vector2Int(1, 0);
    static final Vector2Int Down = new Vector2Int(-1, 0);
    static final Vector2Int Left = new Vector2Int(0, 1);
    static final Vector2Int Right = new Vector2Int(0, -1);

    static Vector2Int add(Vector2Int a, Vector2Int b) {
        return new Vector2Int(a.x + b.x, a.y + b.y);
    }

    int x;
    int y;

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
