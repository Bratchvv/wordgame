package com.wordgame.generator.service;

import com.wordgame.generator.algorithm.ReadFileWord;
import com.wordgame.management.entity.GameWords;
import com.wordgame.management.repository.GameWordsRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Bratchikov
 */
@Service
@RequiredArgsConstructor
@Getter
@Slf4j
@Order(1)
public class InMemDictionaryService {

    private final List<String> inMemWordDictionary = new ArrayList<>();
    private final GameWordsRepository gameWordsRepository;

    private ReadFileWord inMemReadFileWord = null;

    @PostConstruct
    public void afterInit() {
        log.info("Запуск механизма загрузки словаря, при инициализации приложения");
        var time = System.currentTimeMillis();
        fullUpdateFromEntity(gameWordsRepository.findByActive(true));
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
        log.info("Динамическое обновление словаря");
        inMemWordDictionary.clear();
        inMemWordDictionary.addAll(words);
        inMemReadFileWord = new ReadFileWord(words);
    }

    public void fullUpdateFromEntity(GameWords gameWords) {
        List<String> words = new LinkedList<>(Arrays.asList(
            new String(Base64.getDecoder().decode((gameWords.getData().getBytes()))).split(",")));
        fullUpdate(words);
    }
}
