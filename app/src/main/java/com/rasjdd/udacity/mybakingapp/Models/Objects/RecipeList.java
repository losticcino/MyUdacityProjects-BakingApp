package com.rasjdd.udacity.mybakingapp.Models.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeList {

    @SerializedName("")
    @Expose
    private ArrayList<Recipe> recipes = null;


    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
