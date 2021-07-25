package com.wordgame.generator.service;

import com.wordgame.generator.algorithm.ReadFileWord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Vladimir Bratchikov
 */
@Service
@RequiredArgsConstructor
@Getter
@Slf4j
@Order(1)
public class InMemDictionaryService {

    @Value("${dictionary.source}")
    private String dictionaryFilePath;

    private final List<String> inMemWordDictionary = new ArrayList<>();
    private ReadFileWord inMemReadFileWord = null;

    @PostConstruct
    public void afterInit() {
        log.info("Запуск механизма загрузки словаря, при инициализации приложения");
        var time = System.currentTimeMillis();
        fullUpdateFromFile();
        log.info("Словарь успешно загружен. Размер словаря: {}. Время загрузки: {}мс",
                getInMemWordDictionary().size(),
                (System.currentTimeMillis() - time));
    }

    public void addNewWord(String word) {
        inMemWordDictionary.add(word);
        inMemReadFileWord = new ReadFileWord(inMemWordDictionary);
    }

    public void addNewWords(List<String> words) {
        inMemWordDictionary.addAll(words);
        inMemReadFileWord = new ReadFileWord(inMemWordDictionary);
    }

    public void fullUpdate(List<String> words) {
        inMemWordDictionary.clear();
        inMemWordDictionary.addAll(words);
        inMemReadFileWord = new ReadFileWord(words);
    }

    public void fullUpdateFromFile() {
        List<String> words = new LinkedList<>();
        try (Stream<String> lines = Files.lines(
                Paths.get(dictionaryFilePath), Charset.defaultCharset())) {
            lines.forEachOrdered(words::add);
        } catch (Exception e) {

            e.printStackTrace();
            return;
        }
        fullUpdate(words);
    }
}
