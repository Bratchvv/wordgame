package com.wordgame.generator.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@Slf4j
public class ReadFileWordTest {

    private List<String> words;

    @BeforeEach
    public void prepare() {
        words = ReadWordsUtil.readWordsFile(getClass().getClassLoader());
    }

    @Test
    void readWordsTxt() {
        assertEquals("Размер прочитанного списка слов не совпадает с файлом",
                89140, words.size());
        assertEquals("Первое прочитанное влово не совпадает с файлом",
                "абажур", words.get(0));
        assertEquals("Последнее прочитанное влово не совпадает с файлом",
                "яснеть", words.get(words.size()-1));
    }

    @Test
    void readAndBuildTree() {
        var readFileWord = new ReadFileWord(words);
        assertEquals("Размер readFileWord.letters не совпадает с ожидаемым",
                41, readFileWord.letters.length);
        assertEquals("Размер прочитанного списка слов не совпадает с ожидаемым",
                89139,  readFileWord.charStrings.size());
        assertEquals("Размер прочитанного дерева слов не совпадает с ожидаемым",
                30,  readFileWord.treeWord.size());
        assertEquals("Размер кол-ва слов для 'а' не совпадает с ожидаемым",
                25,  readFileWord.treeWord.get('а').nextLetter.size());
    }

}
