package com.wordgame.generator.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import static com.wordgame.generator.model.BonusVariant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@Slf4j
@ActiveProfiles("test")
public class GeneratorSquareTest {

    private ReadFileWord readFileWord;
    private int countColumn = 5;
    private int countRow = 5;
    private int maxIteration = 10;
    private int minCountWord = 100;


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

        var generator = new GenerateSquare(readFileWord,countColumn,countRow, maxIteration, minCountWord);
        var generatorSpy = Mockito.spy(generator);
        Mockito.doReturn(TEST_CHARS).when(generatorSpy).getCell(any());
        var chars = generatorSpy.maxCountGenerateBox();

        assertEquals("Размер сгенерированного списка слов не совпадает с ожидаемым",
                107, generatorSpy.betterAllWord.size());
        assertEquals("Размер сгенерированного набора символов не совпадает с ожидаемым",
                25, chars.length);
        assertEquals("Первое прочитанное влово не совпадает с первым в списке",
                "ад", generatorSpy.betterAllWord.get(0));
        assertEquals("Последнее прочитанное влово не совпадает с последним в списке",
                "урка", generatorSpy.betterAllWord.get(generatorSpy.betterAllWord.size()-1));
    }
    @Test
    void checkGeneratedLetters() {

        var generator = new GenerateSquare(readFileWord,countColumn,countRow, maxIteration, minCountWord);
        var chars = generator.maxCountGenerateBox();
        assertEquals("Размер сгенерированного набора символов не совпадает с ожидаемым",
                25, chars.length);
    }

    @Test
    void checkBonus() {
        var bonus = BonusAlgorithm.generateBonusPosition(countRow, countColumn);
        bonus.forEach((k,v) ->
                assertTrue("Вектор " + k + " выходит за допустимые рамки",
                        k.x >=0 && k.x < countRow && k.y >=0 && k.y < countColumn));
        bonus.forEach((k,v) ->
                assertTrue("Вектор " + k + " содержит недопустисый бонус",
                        v.equals(x2) || v.equals(x3) || v.equals(c2) || v.equals(c3)));
    }

}
