package com.rasjdd.udacity.mybakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.rasjdd.udacity.mybakingapp.fragments.RecipeListFragment;
import com.rasjdd.udacity.mybakingapp.models.Recipe;
import com.rasjdd.udacity.mybakingapp.utilities.Constants;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeListClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = new Intent(this, StepDetailActivity.class);
        intent.putExtra(Constants.keyFullRecipe, recipe);
        startActivity(intent);
    }
}
