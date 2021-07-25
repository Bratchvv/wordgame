package com.wordgame.generator.algorithm;

import com.google.common.collect.Lists;
import com.wordgame.generator.model.BonusVariant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Bratchikov
 */
public class BonusAlgorithm {

    static Map<Vector2Int, BonusVariant> generateBonusPosition(int countRow, int countColumn) {
        var dict = new HashMap<Vector2Int, BonusVariant>();
        // создаем лист из бонусов
        var bonusArray = Lists.newArrayList(BonusVariant.C_2, BonusVariant.C_3, BonusVariant.X_2, BonusVariant.X_3);

        // заполняем словарь
        while (dict.size() < 4) {
            var vector = new Vector2Int(getRandomNumber(0, countRow), getRandomNumber(0, countColumn));
            if (!dict.containsKey(vector)) {
                var bonus = bonusArray.get(getRandomNumber(0, bonusArray.size()));
                dict.put(vector, bonus);
                bonusArray.remove(bonus);
            }
        }

        return dict;
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
