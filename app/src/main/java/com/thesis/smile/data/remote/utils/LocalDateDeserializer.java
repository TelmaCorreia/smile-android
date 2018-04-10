package com.thesis.smile.data.remote.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

import java.lang.reflect.Type;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        LocalDate localDate = LocalDate.parse(json.getAsJsonPrimitive().getAsString());
        return LocalDate.from(localDate);
    }
}