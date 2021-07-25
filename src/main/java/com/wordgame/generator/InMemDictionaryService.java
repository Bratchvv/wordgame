package com.wordgame.generator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
public class InMemDictionaryService {

    @Value("${dictionary.source}")
    private String dictionaryFilePath;

    private final List<String> inMemWordDictionary = new ArrayList<>();

    public void addNewWord(String word) {
        inMemWordDictionary.add(word);
    }

    public void addNewWords(List<String> words) {
        inMemWordDictionary.addAll(words);
    }

    public void fullUpdate(List<String> words) {
        inMemWordDictionary.clear();
        inMemWordDictionary.addAll(words);
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
