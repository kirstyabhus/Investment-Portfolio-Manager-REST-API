package com.cbfacademy.apiassessment.utility;

import org.springframework.stereotype.Component;

import com.cbfacademy.apiassessment.model.ETF;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Stock;
import com.google.gson.*;

@Component
public class InvestmentDeserialiser implements JsonDeserializer<Investment> {

    @Override
    public Investment deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        // deseralise investment based on "type" field
        if ("Stock".equals(type)) {
            return context.deserialize(jsonObject, Stock.class);
        } else if ("ETF".equals(type)) {
            return context.deserialize(jsonObject, ETF.class);
        } else {
            throw new JsonParseException("Unknown investment type: " + type);
        }
    }
}