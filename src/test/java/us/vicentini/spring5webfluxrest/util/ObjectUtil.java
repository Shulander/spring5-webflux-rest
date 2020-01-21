package us.vicentini.spring5webfluxrest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtil {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    private ObjectUtil() {
    }


    public static String asJsonString(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
