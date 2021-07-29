package com.wordgame.management.entity;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordgame.management.dto.GameCategoriesData;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.SneakyThrows;
import org.reflections.serializers.JsonSerializer;

/**
 * @author Vladimir Bratchikov
 */

@Converter
@SuppressWarnings("all")
public class ToEntityJsonConverter implements AttributeConverter<Object, String> {

    private final static ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Object entity) {
        return mapper.writeValueAsString(entity);
    }

    @SneakyThrows
    @Override
    public Object convertToEntityAttribute(String joined) {
        return (Object) mapper.readValue(joined, GameCategoriesData.class);
    }

}
