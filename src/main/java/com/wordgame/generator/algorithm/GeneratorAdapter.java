package com.wordgame.generator.algorithm;

import com.google.common.collect.Lists;
import com.wordgame.generator.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class GeneratorAdapter {

    public GeneratorResult generate(GeneratorInputParams inputParams) {
        var read = processReadFileWord(inputParams);
        var generator = processGenerateSquare(read,inputParams);
        var chars = generator.maxCountGenerateBox();
        var bonus = BonusAlgorithm.generateBonusPosition(inputParams.getCountRow(), inputParams.getCountColumn());
        logStatistics(generator, chars, bonus);
        List<Bonus> bonuses = new ArrayList<>(4);
        bonus.forEach((k,v) -> bonuses.add(Bonus.builder().vector(new Vector(k.x, k.y)).bonusVariant(v).build()));

        var charsList = new ArrayList<Character>();
        for(var c: chars) {
            charsList.add(c);
        }

        return GeneratorResult.builder()
                .letters(charsList)
                .words(generator.betterAllWord)
                .bonus(bonuses)
                .build();
    }

    ReadFileWord processReadFileWord(GeneratorInputParams inputParams) {
        return new ReadFileWord(inputParams.getDictionaryWords());
    }

    GenerateSquare processGenerateSquare(ReadFileWord read, GeneratorInputParams inputParams) {
        return new GenerateSquare(read,inputParams.getCountColumn(), inputParams.getCountRow());
    }

    private void logStatistics(GenerateSquare generator, char[] chars, Map<Vector2Int, BonusVariant> bonus) {
        StringBuilder sbLetters = new StringBuilder("[");
        for (int i = 0; i < generator.countRow; i++)
        {
            for (int j = 0; j < generator.countColumn; j++)
            {
                sbLetters.append(chars[i * generator.countRow + j]).append(",");
            }
        }
        sbLetters.append("]");

        StringBuilder sbBonus = new StringBuilder("[");
        bonus.forEach((k,v) -> sbBonus.append(k).append("-").append(v));
        sbBonus.append("]");

        log.info("Generate letters: {}, words: {}, bonus: {}", sbLetters, generator.betterAllWord.size(), sbBonus);
    }
}
