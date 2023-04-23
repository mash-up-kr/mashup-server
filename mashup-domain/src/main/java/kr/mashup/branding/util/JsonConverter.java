package kr.mashup.branding.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

@Converter
public class JsonConverter implements AttributeConverter<Map<String, Object>, String> {

    protected final ObjectMapper objectMapper;

    public JsonConverter() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        try {
            TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
            };
            return objectMapper.readValue(dbData, typeReference);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
