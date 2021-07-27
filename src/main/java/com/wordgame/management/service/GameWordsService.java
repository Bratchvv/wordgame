package com.wordgame.management.service;

import com.wordgame.generator.service.InMemDictionaryService;
import com.wordgame.management.dto.GamePropertyDto;
import com.wordgame.management.dto.GameWordsDto;
import com.wordgame.management.dto.GameWordsInfoDto;
import com.wordgame.management.entity.GameProperty;
import com.wordgame.management.entity.GameWords;
import com.wordgame.management.repository.GamePropertyRepository;
import com.wordgame.management.repository.GameWordsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public GameWordsInfoDto getActiveWords(Integer category) {
        return gameWordsRepository.findActiveInfo(category);
    }

    @Transactional
    public void addNewWords(String name, Integer category, String data) {
        GameWords activeInfo = gameWordsRepository.findByCategoryAndActive(category, true);
        if(activeInfo != null) {
            activeInfo.setActive(false);
            gameWordsRepository.save(activeInfo);
        }
        GameWordsDto dto = new GameWordsDto();
        dto.setActive(true);
        dto.setCategory(category);
        dto.setDate(LocalDateTime.now());
        dto.setName(name);
        dto.setData(data);
        GameWords words = gameWordsRepository.save(modelMapper.map(dto, GameWords.class));
        inMemDictionaryService.fullUpdateFromEntity(words);
    }
}
