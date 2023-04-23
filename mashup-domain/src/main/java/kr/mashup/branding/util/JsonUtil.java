package kr.mashup.branding.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mashup.branding.domain.exception.JsonDeserializeException;
import kr.mashup.branding.domain.exception.JsonSerializeException;

import java.io.IOException;
import java.util.Map;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> deserialize(String jsonString) {
        try {
            TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
            };
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            throw new JsonDeserializeException();
        }
    }

    public static String serialize(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new JsonSerializeException();
        }
    }
}
