package com.wordgame.generator.algorithm;

import com.wordgame.generator.model.GeneratorInputParams;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.wordgame.generator.model.BonusVariant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@Slf4j
public class GeneratorAdapterTest {

    private ReadFileWord readFileWord;
    private int countColumn = 5;
    private int countRow = 5;
    private GeneratorInputParams generatorInputParams;

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
       generatorInputParams = GeneratorInputParams.builder()
               .dictionaryWords(words)
               .countColumn(countColumn)
               .countRow(countRow)
               .build();
       readFileWord = new ReadFileWord(words);
    }

    @Test
    void test() {
        var generator = new GeneratorAdapter();
        var generatorSpy = Mockito.spy(generator);

        var generateSquare = new GenerateSquare(readFileWord,
                generatorInputParams.getCountColumn(),generatorInputParams.getCountRow());
        var generateSquareSpy = Mockito.spy(generateSquare);
        Mockito.doReturn(TEST_CHARS).when(generateSquareSpy).getCell(any());
        Mockito.doReturn(generateSquareSpy).when(generatorSpy).processGenerateSquare(any(), any());

        var result = generatorSpy.generate(generatorInputParams);

        assertEquals("Размер сгенерированного списка слов не совпадает с ожидаемым",
                107, result.getWords().size());
        assertEquals("Размер сгенерированного набора символов не совпадает с ожидаемым",
                25, result.getLetters().size());
        assertEquals("Первое прочитанное влово не совпадает с первым в списке",
                "ад", result.getWords().get(0));
        assertEquals("Последнее прочитанное влово не совпадает с последним в списке",
                "урка", result.getWords().get(result.getWords().size()-1));
        assertEquals("Размер бонусов не совпадает сожидаемым",
                4, result.getBonus().size());
    }

}
