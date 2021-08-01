package com.wordgame.core;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordgame.gameplay.dto.PlayerGameCategoriesData;
import com.wordgame.management.dto.GameCategoriesData;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Vladimir Bratchikov
 */

@Converter
@SuppressWarnings("all")
public class ToPlayerGameCategoriesDataJsonConverter implements AttributeConverter<Object, String> {

    private final static ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Object entity) {
        return mapper.writeValueAsString(entity);
    }

    @SneakyThrows
    @Override
    public Object convertToEntityAttribute(String joined) {
        return (Object) mapper.readValue(joined, PlayerGameCategoriesData.class);
    }

}
