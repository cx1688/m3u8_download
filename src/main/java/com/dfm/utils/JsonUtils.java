package com.dfm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-28 22:33
 */
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> String parseJsonString(T t) throws JsonProcessingException {
        return mapper.writeValueAsString(t);
    }

    public static <T> T parseJsonObject(String content, Class clazz) throws JsonProcessingException {
        return (T) mapper.readValue(content, clazz);
    }

    public static <T> List<T> parseJsonList(String content,Class<? extends List> listClass,Class clazz) throws JsonProcessingException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(listClass, clazz);
        return  mapper.readValue(content, javaType);
    }
    public static <T> T readJson(String jsonStr,Class clazz) throws JsonProcessingException {
       return (T) mapper.readValue(jsonStr,clazz);
    }
}
