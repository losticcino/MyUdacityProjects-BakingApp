package com.rasjdd.udacity.mybakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class Navigation {

    public static void getMeToRecipeDetail(int recipePosition) {
        onView(withId(R.id.containerRecipeListFragment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));

    }

    public static void selectRecipeStep(int recipeStep) {
        onView(withId(R.id.containerStepsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipeStep, click()));
    }

}
