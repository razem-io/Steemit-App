package io.razem.steemitapp.controller;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import io.razem.steemitapp.model.Discussion;

/**
 * Created by julia on 18.07.2016.
 */
public class DiscussionDeserializer implements JsonDeserializer<Discussion> {

    @Override
    public Discussion deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String title = jsonObject.get("title").getAsString();
        String body = jsonObject.get("body").getAsString();

        Discussion discussion = new Discussion();
        discussion.setTitle(title);
        discussion.setBody(body);

        return discussion;
    }
}
