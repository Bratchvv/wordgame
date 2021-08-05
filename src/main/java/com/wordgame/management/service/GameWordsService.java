package com.wordgame.management.service;

import com.wordgame.generator.service.InMemDictionaryService;
import com.wordgame.management.dto.GameWordsDto;
import com.wordgame.management.dto.GameWordsInfoDto;
import com.wordgame.management.entity.GameWords;
import com.wordgame.management.repository.GameWordsRepository;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Bratchikov
 */
@Service
@RequiredArgsConstructor
public class GameWordsService {

    private final GameWordsRepository gameWordsRepository;
    private final InMemDictionaryService inMemDictionaryService;
    private final ModelMapper modelMapper;

    public List<GameWordsInfoDto> getList() {
        return gameWordsRepository.findAllInfo().stream()
            .map(p -> modelMapper.map(p, GameWordsInfoDto.class))
            .sorted(Comparator.comparing(GameWordsInfoDto::getId))
            .collect(Collectors.toList());
    }

    public Page<GameWordsInfoDto> getPages(Pageable pageable) {
        return gameWordsRepository.findAll(pageable)
            .map(v -> modelMapper.map(v, GameWordsInfoDto.class));
    }

    public GameWordsInfoDto getActiveWords() {
        return gameWordsRepository.findActiveInfo();
    }

    @Transactional
    public void addNewWords(String name, String data) {
        GameWords activeInfo = gameWordsRepository.findByActive(true);
        if (activeInfo != null) {
            activeInfo.setActive(false);
            gameWordsRepository.save(activeInfo);
        }
        GameWordsDto dto = new GameWordsDto();
        dto.setActive(true);
        dto.setDate(LocalDateTime.now());
        dto.setName(name);
        dto.setData(new String(
            Base64.encodeBase64(String.join(", ", data
                .replaceAll("\r", "")
                .split("\n")).getBytes(StandardCharsets.UTF_8)))
        );
        GameWords words = gameWordsRepository.save(modelMapper.map(dto, GameWords.class));
        inMemDictionaryService.fullUpdateFromEntity(words);
    }

    @Transactional
    public void activateWords(Long id) {
        GameWords activeInfo = gameWordsRepository.findByActive(true);
        if (activeInfo != null) {
            activeInfo.setActive(false);
            gameWordsRepository.save(activeInfo);
            GameWords newActiveInfo = gameWordsRepository.findById(id).orElseThrow(() -> {
                activeInfo.setActive(true);
                gameWordsRepository.save(activeInfo);
                throw new RuntimeException("Не удалось сделать словарь активным. Выполнен откат");
            });
            if (newActiveInfo != null) {
                newActiveInfo.setActive(true);
                gameWordsRepository.save(newActiveInfo);
                inMemDictionaryService.fullUpdateFromEntity(newActiveInfo);
            }
        } else {
            throw new RuntimeException("Не удалось найти активный словарь");
        }
    }
}
