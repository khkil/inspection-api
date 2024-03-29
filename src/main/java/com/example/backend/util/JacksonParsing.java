package com.example.backend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@Slf4j
public class JacksonParsing {
    private static String CHARSET = "utf-8";

    public static Map<String, Object> toMap(String json) {
        Map<String, Object> result = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            log.error("json string map parsing error");
        }

        return result;
    }

    public static List toList(String json) {
        List<?> result = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(json, new TypeReference<List<?>>() {});
        } catch (IOException e) {
            log.error("json string list parsing error");
        }
        return result;
    }

    public static String toString(Object object) {
        return toString(object, CHARSET);
    }
    public static String toString(Object object, String charset) {
        ByteArrayOutputStream output = null;
        Writer write = null;
        String data = null;

        try{
            output = new ByteArrayOutputStream();
            write = new OutputStreamWriter(output, charset);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(write, object);
            data = output.toString(charset);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if(output != null) try { output.close(); } catch (IOException e) { }
            if(write != null) try { write.close(); } catch (IOException e) { }
        }

        return data;
    }
}