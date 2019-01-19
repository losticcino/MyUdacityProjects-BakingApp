package com.rasjdd.udacity.mybakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

public class BaseTest {
    protected AppSupervisor appSupervisor;
    protected IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        appSupervisor = (AppSupervisor) activityTestRule.getActivity().getApplicationContext();
        mIdlingResource = appSupervisor.getIdlingResource();

        //Register Idling Resources.
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null)
            IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
}
