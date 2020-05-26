package com.pmu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmu.exception.BaseException;
import com.pmu.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class JsonUtils {

    private static ObjectMapper mapper;


    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper){
        JsonUtils.mapper = objectMapper;
    }

    public static String serialize(Object object) {
        try {
            return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException exception) {
            throw new BaseException(ErrorCode.CANNOT_MAP_RESPONSE);
        }
    }

    public static <T> T deserialize(String content, Class<T> clazz) {
        if (content == null) {
            return null;
        }
        if (clazz == String.class) {
            return (T) content;
        }
        try {
            return mapper.readValue(content, clazz);

        }
        catch (Exception exception) {
            throw new BaseException(ErrorCode.CANNOT_MAP_RESPONSE);
        }
    }

    public static <T> T deserialize(String content, TypeReference<T> typeReference) {
        if (content == null) {
            return null;
        }
        if (typeReference.getType() == String.class) {
            return (T) content;
        }
        try {
            return mapper.readValue(content, typeReference);

        }
        catch (Exception exception) {
            throw new BaseException(ErrorCode.CANNOT_MAP_RESPONSE);
        }
    }

    public static <T> T deserialize(JsonNode node, Class<T> clazz) {
        try {
            return mapper.treeToValue(node, clazz);
        }
        catch (Exception exception) {
            throw new BaseException(ErrorCode.CANNOT_MAP_RESPONSE);
        }
    }

    public static <T> T deserialize(String content, Type type) {
        try {
            return mapper.readValue(content, mapper.getTypeFactory().constructType(type));
        }
        catch (Exception exception) {
            throw new BaseException(ErrorCode.CANNOT_MAP_RESPONSE);
        }
    }

    public static JsonNode deserialize(String content) {
        try {
            return mapper.readTree(content);
        }
        catch (Exception exception) {
            throw new BaseException(ErrorCode.CANNOT_MAP_RESPONSE);
        }
    }

    public static <T> T convert(Object object, Class<T> clazz) {
        return mapper.convertValue(object, clazz);
    }
}