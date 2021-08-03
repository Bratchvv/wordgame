package com.wordgame.service;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.wordgame.generator.algorithm.ReadWordsUtil;
import com.wordgame.generator.service.InMemDictionaryService;
import com.wordgame.management.repository.GameWordsRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class InMemDictionaryServiceTest {

    private final GameWordsRepository gameWordsRepository = mock(GameWordsRepository.class);
    private List<String> words;

    @BeforeEach
    public void prepare() {
        words = ReadWordsUtil.readWordsFile(getClass().getClassLoader());
    }
    @Test
    public void test() {
        var service = new InMemDictionaryService(gameWordsRepository);
        service.fullUpdate(words);
        assertEquals("Размер списка слов не совпадает с ожидаемым",
                89140,  service.getInMemWordDictionary().size());

        var prevList = service.getInMemWordDictionary();
        var prevReader = service.getInMemReadFileWord();
        words = new ArrayList<>(words);
        words.add("новоеСлово");
        service.fullUpdate(words);
        var newList = service.getInMemWordDictionary();
        var newReader = service.getInMemReadFileWord();
        assertSame(prevList, newList);
        assertNotSame(prevReader, newReader);
        assertEquals("Размер списка слов не совпадает с ожидаемым",
                89141,  service.getInMemWordDictionary().size());
        assertEquals("Новый елемент отсутствует на своей позиции",
                "новоеСлово",  service.getInMemWordDictionary().get(89140));

    }
}
