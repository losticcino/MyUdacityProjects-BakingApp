package com.rasjdd.udacity.mybakingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.rasjdd.udacity.mybakingapp.models.Recipe;

public class AppUtilities {

    public static boolean saveRecipe(Context context, Recipe recipe) {

        // Serialize the recipe with GSON
        Gson gson = new Gson();
        String serialRecipe = gson.toJson(recipe);

        // Save to Shared Preferences
        SharedPreferences.Editor spe = context.getSharedPreferences(Constants.keyAppPreferences, Context.MODE_PRIVATE).edit();
        spe.putString(Constants.keyWidgetRecipe, serialRecipe);
        return spe.commit();
    }

    public static Recipe loadRecipe(Context context) {

        // Retrieve from Shared Preferences
        SharedPreferences preferences = context.getSharedPreferences(Constants.keyAppPreferences, Context.MODE_PRIVATE);
        String serilRecipe = preferences.getString(Constants.keyWidgetRecipe, Constants.InvalidString);

        // verify a valid response
        assert serilRecipe != null;
        if (!serilRecipe.equals(Constants.InvalidString)) {
            Gson gson = new Gson();
            return gson.fromJson(serilRecipe, Recipe.class);
        }

        return null;
    }
}
