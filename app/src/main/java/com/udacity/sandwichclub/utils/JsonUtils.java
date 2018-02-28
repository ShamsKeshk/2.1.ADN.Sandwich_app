package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichJsonObject = new JSONObject(json);
            String mainName = "";
            List<String> alsoKnownAs = null;
            String placeOfOrigin = "";
            String description = "";
            String image = "";
            List<String> ingredients = new ArrayList<>();

            if (sandwichJsonObject.has("name")) {
                JSONObject jsonMainName = sandwichJsonObject.getJSONObject("name");
                if (jsonMainName.has("mainName")) {
                    mainName = jsonMainName.getString("mainName");
                }
                if (jsonMainName.has("alsoKnownAs")) {

                    JSONArray alsoKnownAsArray = jsonMainName.getJSONArray("alsoKnownAs");

                    for (int i = 0; i < alsoKnownAsArray.length(); i++) {

                        if (alsoKnownAsArray.getString(i) != null) {
                            alsoKnownAs = new ArrayList<>();

                            alsoKnownAs.add(alsoKnownAsArray.getString(i));
                        }
                    }
                }
            }

            if (sandwichJsonObject.has("ingredients")) {
                JSONArray ingredientsArray = sandwichJsonObject.getJSONArray("ingredients");
                for (int i = 0; i < ingredientsArray.length(); i++) {

                    if (ingredientsArray.getString(i) != null) {
                        ingredients.add(ingredientsArray.getString(i));
                    }
                }
            }
            if (sandwichJsonObject.has("placeOfOrigin")) {
                placeOfOrigin = sandwichJsonObject.getString("placeOfOrigin");
            }
            if (sandwichJsonObject.has("description")) {
                description = sandwichJsonObject.getString("description");
            }
            if (sandwichJsonObject.has("image")) {
                image = sandwichJsonObject.getString("image");
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
