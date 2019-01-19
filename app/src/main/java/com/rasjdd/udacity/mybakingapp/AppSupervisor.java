package com.rasjdd.udacity.mybakingapp;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.rasjdd.udacity.mybakingapp.idler.RecipesIdler;

public class AppSupervisor extends Application {

    @Nullable
    private RecipesIdler mIdler;

    @VisibleForTesting
    @NonNull
    private IdlingResource initializeIdler() {
        if (mIdler == null) {
            mIdler = new RecipesIdler();
        }
        return mIdler;
    }

    public AppSupervisor() {

        // Make sure we only use this in debug
        if (BuildConfig.DEBUG) {
            initializeIdler();
        }

    }

    public void setIdleState(boolean state) {
        if (mIdler != null) mIdler.setIdlerState(state);
    }

    @Nullable
    public RecipesIdler getIdlingResource() {
        return mIdler;
    }
}
