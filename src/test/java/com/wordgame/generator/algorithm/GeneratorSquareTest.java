package com.wordgame.generator.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.wordgame.generator.algorithm.BonusVariant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@Slf4j
public class GeneratorSquareTest {

    private ReadFileWord readFileWord;
    private int countColumn = 5;
    private int countRow = 5;

    private static final char[][] TEST_CHARS = new char[][] {
            {'п', 'д', 'л', 'д', 'з' },
            {'а', 'з', 'а', 'д', 'м' },
            {'с', 'з', 'к', 'р', 'у'},
            {'в', 'о', 'к', 'ь', 'з'},
            { 'о', 'с', 'о', 'н', 'л'},
    };

    @BeforeEach
    public void prepare() {
       var words = ReadWordsUtil.readWordsFile(getClass().getClassLoader());
       readFileWord = new ReadFileWord(words);
    }

    @Test
    void checkGeneratedWords() {

        var generator = new GenerateSquare(readFileWord,countColumn,countRow);
        var generatorSpy = Mockito.spy(generator);
        Mockito.doReturn(TEST_CHARS).when(generatorSpy).getCell(any());
        var chars = generatorSpy.maxCountGenerateBox();

        assertEquals("Размер сгенерированного списка слов не совпадает с ожидаемым",
                107, generatorSpy.betterAllWord.size());
        assertEquals("Первое прочитанное влово не совпадает с первым в списке",
                "ад", generatorSpy.betterAllWord.get(0));
        assertEquals("Последнее прочитанное влово не совпадает с последним в списке",
                "урка", generatorSpy.betterAllWord.get(generatorSpy.betterAllWord.size()-1));
    }

    @Test
    void checkBonus() {
        var bonus = Bonus.generateBonusPosition(countRow, countColumn);
        bonus.forEach((k,v) ->
                assertTrue("Вектор " + k + " выходит за допустимые рамки",
                        k.x >=0 && k.x < countRow && k.y >=0 && k.y < countColumn));
        bonus.forEach((k,v) ->
                assertTrue("Вектор " + k + " содержит недопустисый бонус",
                        v.equals(X_2) || v.equals(X_3) || v.equals(C_2) || v.equals(C_3)));
    }

}
