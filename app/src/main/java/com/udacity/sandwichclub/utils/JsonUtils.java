package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static final String placeOfOriginUtit = "placeOfOrigin";
    public static final String descriptionUtit = "description";
    public static final String imageUtit = "image";
    public static final String ingredientsUtit = "ingredients";
    public static final String nameUtit = "name";
    public static final String mainNameUtit = "mainName";
    public static final String alsoKnownAsUtit = "alsoKnownAs";


    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;

        List<String> ingredientsList = new ArrayList<>();
        List<String> alsoKnownAsList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            String placeOfOrigin = jsonObject.optString(placeOfOriginUtit);
            String description = jsonObject.optString(descriptionUtit);
            String image = jsonObject.optString(imageUtit);

            JSONArray jsonArray = jsonObject.optJSONArray(ingredientsUtit);
            for (int i = 0 ; i < jsonArray.length() ; i++){
                ingredientsList.add(jsonArray.optString(i));
            }

            JSONObject namesObject = jsonObject.optJSONObject(nameUtit);
            String mainName = namesObject.getString(mainNameUtit);

            JSONArray alsoKnownAs =namesObject.optJSONArray(alsoKnownAsUtit);
            for (int i = 0 ; i < alsoKnownAs.length() ; i++){
                alsoKnownAsList.add(alsoKnownAs.optString(i));
            }
            
            sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
