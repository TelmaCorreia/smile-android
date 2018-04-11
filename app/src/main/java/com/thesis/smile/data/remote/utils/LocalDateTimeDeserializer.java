package com.thesis.smile.data.remote.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.threeten.bp.LocalDateTime;

import java.lang.reflect.Type;

public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        LocalDateTime localDateTime = LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
        return LocalDateTime.from(localDateTime);
    }

}