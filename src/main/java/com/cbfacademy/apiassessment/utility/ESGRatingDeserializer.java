package com.cbfacademy.apiassessment.utility;

import com.cbfacademy.apiassessment.model.ESGRating;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Custom deserializer for ESGRating enum to handle JSON deserialization.
 */
public class ESGRatingDeserializer implements JsonDeserializer<ESGRating> {

    /**
     * Deserializes the JSON element into ESGRating enum.
     *
     * @param json    The JSON element to deserialize.
     * @param typeOfT The type of the Object to deserialize to.
     * @param context The context for deserialization that may be used to delegate
     *                deserialization of
     *                other objects within this method.
     * @return The deserialized ESGRating enum.
     * @throws JsonParseException if an error occurs while parsing the JSON element.
     */
    @Override
    public ESGRating deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            String ratingString = json.getAsString();

            // Check if the ratingString is blank or absent in the JSON
            if (ratingString == null || ratingString.trim().isEmpty()) {
                // Set a default value
                return ESGRating.UNSPECIFIED;
            }

            // If not blank, parse the ESGRating normally
            return ESGRating.valueOf(ratingString);
        } catch (IllegalArgumentException e) {
            // Handle any exceptions due to invalid enum value in the JSON
            throw new JsonParseException("Invalid ESGRating value: " + e.getMessage(), e);
        } catch (Exception e) {
            // Handle other general exceptions during deserialization
            throw new JsonParseException("Error while deserializing ESGRating: " + e.getMessage(), e);
        }
    }
}
