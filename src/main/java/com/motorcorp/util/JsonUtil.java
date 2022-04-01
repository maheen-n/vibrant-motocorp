package com.motorcorp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Error converting object to string", e);
            return "";
        }
    }

    public static <T> T toObject(Object obj, Class<T> toClass) {
        try {
            if (obj instanceof String)
                return OBJECT_MAPPER.readValue((String) obj, toClass);
            return OBJECT_MAPPER.readValue(toString(obj), toClass);
        } catch (Exception e) {
            log.error("Error converting object to string", e);
            return null;
        }
    }

    public static <T> T toObject(Object obj, TypeReference<T> toClass) {
        try {
            if (obj instanceof String)
                return OBJECT_MAPPER.readValue((String) obj, toClass);
            return OBJECT_MAPPER.readValue(toString(obj), toClass);
        } catch (Exception e) {
            log.error("Error converting object to string", e);
            return null;
        }
    }
}
