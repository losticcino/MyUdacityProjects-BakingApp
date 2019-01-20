package com.rasjdd.udacity.mybakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rasjdd.udacity.mybakingapp.fragments.RecipeListFragment;
import com.rasjdd.udacity.mybakingapp.models.Recipe;
import com.rasjdd.udacity.mybakingapp.utilities.Constants;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeListClickListener {

    public static boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = getResources().getBoolean(R.bool.mTwoPane);
    }


    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = new Intent(this, StepDetailActivity.class);
        intent.putExtra(Constants.keyFullRecipe, recipe);
        intent.putExtra(Constants.keyLayoutMode, mTwoPane);
        startActivity(intent);
    }
}
