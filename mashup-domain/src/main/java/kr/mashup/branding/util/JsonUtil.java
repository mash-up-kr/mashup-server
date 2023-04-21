package kr.mashup.branding.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, String> jsonToMap(String jsonString) throws IOException {
        TypeReference<Map<String, String>> typeReference = new TypeReference<>() {
        };
        return objectMapper.readValue(jsonString, typeReference);
    }

    public static String serialize(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
