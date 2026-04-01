package com.naixue.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 灵活的LocalDateTime反序列化器
 *
 * 支持多种日期时间格式:
 * - yyyy-MM-dd HH:mm:ss (标准格式)
 * - yyyy-MM-ddTHH:mm:ss (ISO格式)
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public class FlexibleLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter STANDARD_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getValueAsString();
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            // 尝试标准格式
            return LocalDateTime.parse(value, STANDARD_FORMAT);
        } catch (DateTimeParseException e1) {
            try {
                // 尝试ISO格式(T作为分隔符)
                return LocalDateTime.parse(value, ISO_FORMAT);
            } catch (DateTimeParseException e2) {
                throw new IOException("无法解析日期时间: " + value);
            }
        }
    }
}