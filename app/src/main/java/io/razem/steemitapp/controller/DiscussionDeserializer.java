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
        Integer children = jsonObject.get("children").getAsInt();
        Integer netVotes = jsonObject.get("net_votes").getAsInt();
        String pendingPayoutValue = jsonObject.get("pending_payout_value").getAsString();
        String totalPendingPayoutValue = jsonObject.get("total_pending_payout_value").getAsString();


        Discussion discussion = new Discussion();
        discussion.setTitle(title);
        discussion.setBody(body);
        discussion.setChildren(children);
        discussion.setNetVotes(netVotes);
        discussion.setPendingPayoutValue(pendingPayoutValue);
        discussion.setTotalPendingPayoutValue(totalPendingPayoutValue);

        return discussion;
    }
}
