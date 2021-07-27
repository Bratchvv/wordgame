package com.wordgame.management.service;

import com.wordgame.management.dto.GamePropertyDto;
import com.wordgame.management.entity.GameProperty;
import com.wordgame.management.repository.GamePropertyRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
public class GenerationPropertiesService {

    public static final String BUFER_SIZE = "BufferSize";
    public static final String COUNT_ROW = "CountRow";
    public static final String COUNT_COLUMN = "CountColumn";
    public static final String MAX_ITERATION = "MaxIteration";
    public static final String MIN_COUNT_WORD = "MinCountWord";

    private static final Map<String, Integer> INNER_GENERATION_PARAMS_BUFFER = new HashMap<>();

    private final GamePropertyRepository gamePropertyRepository;
    private final ModelMapper modelMapper;

    public int getBufferSize() {
        return getParam(BUFER_SIZE);
    }

    public int getCountRowSize() {
        return getParam(COUNT_ROW);
    }

    public int getCountColumnSize() {
        return getParam(COUNT_COLUMN);
    }

    public int getMaxIterationSize() {
        return getParam(MAX_ITERATION);
    }

    public int getMinCountWordSize() {
        return getParam(MIN_COUNT_WORD);
    }

    public int getParam(String param) {
        if(INNER_GENERATION_PARAMS_BUFFER.get(param) != null) {
            return INNER_GENERATION_PARAMS_BUFFER.get(param);
        }
        GameProperty property = gamePropertyRepository.findByName(param)
                .orElseThrow(EntityNotFoundException::new);
        INNER_GENERATION_PARAMS_BUFFER.put(param, Integer.parseInt(property.getValue()));
        return INNER_GENERATION_PARAMS_BUFFER.get(param);
    }

    public GamePropertyDto updateParam(String param, String value) {
        if(INNER_GENERATION_PARAMS_BUFFER.get(param) != null) {
            INNER_GENERATION_PARAMS_BUFFER.remove(param);
        }
        GameProperty property = gamePropertyRepository.findByName(param)
                .orElseThrow(EntityNotFoundException::new);
        property.setValue(value);
        gamePropertyRepository.save(property);
        return modelMapper.map(property, GamePropertyDto.class);
    }

    public List<GamePropertyDto> getList() {
        return gamePropertyRepository.findAll().stream()
                .map(p -> modelMapper.map(p, GamePropertyDto.class))
                .sorted(Comparator.comparing(GamePropertyDto::getId))
                .collect(Collectors.toList());
    }
}
