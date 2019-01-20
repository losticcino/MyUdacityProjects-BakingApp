package com.rasjdd.udacity.mybakingapp;

import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import com.rasjdd.udacity.mybakingapp.utilities.Constants;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ThreadLocalRandom;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class BakingAppTests extends BaseTest {

    @Test
    public void clickRecyclerViewItemHasIntentExtraWithRecipeKey() {
        Intents.init();

        Navigation.getMeToRecipeDetail(0);
        intended(hasExtraWithKey(Constants.keyFullRecipe));

        Intents.release();
    }

    @Test
    public void clickOnRecyclerViewItem_opensRecipeInfoActivity() {
        int randomRecipe = ThreadLocalRandom.current().nextInt(0, 4);

        Navigation.getMeToRecipeDetail(randomRecipe);

        onView(withId(R.id.recipe_detail))
                .check(matches(isDisplayed()));

        boolean multiPaneMode = appSupervisor.getResources().getBoolean(R.bool.mTwoPane);

        if (!multiPaneMode) {
            onView(withId(R.id.viewsRecipeStepsPhone))
                    .check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.viewsRecipeStepsTablet))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void clickRecyclerViewStep_opensRecipeDetailsStepActivity() {
        int randomRecipe = ThreadLocalRandom.current().nextInt(0, 4);

        Navigation.getMeToRecipeDetail(randomRecipe);

        boolean multiPaneMode = appSupervisor.getResources().getBoolean(R.bool.mTwoPane);

        if (!multiPaneMode) {
            Intents.init();
            Navigation.selectRecipeStep(1);

            intended(hasComponent(StepDetailActivity.class.getName()));
            intended(hasExtraWithKey(Constants.keyFullRecipe));
            intended(hasExtraWithKey(Constants.keyRecipeStep));
            Intents.release();

            onView(withId(R.id.recipe_detail))
                    .check(matches(isCompletelyDisplayed()));
        } else {
            Navigation.selectRecipeStep(1);

            onView(withId(R.id.playerStepVideo))
                    .check(matches(isDisplayed()));
        }
    }

}
