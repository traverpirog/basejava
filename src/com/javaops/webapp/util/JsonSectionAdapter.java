package com.javaops.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";
    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = jsonPrimitive.getAsString();

        try {
            Class tClass = Class.forName(className);
            return jsonDeserializationContext.deserialize(jsonObject.get(INSTANCE), tClass);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, t.getClass().getName());
        JsonElement jsonElement = jsonSerializationContext.serialize(t);
        jsonObject.add(INSTANCE, jsonElement);
        return jsonObject;
    }
}
