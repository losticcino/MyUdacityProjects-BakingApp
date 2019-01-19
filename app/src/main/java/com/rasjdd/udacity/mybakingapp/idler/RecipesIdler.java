package com.rasjdd.udacity.mybakingapp.idler;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecipesIdler implements IdlingResource {

    @Nullable
    private volatile ResourceCallback resourceCallback;

    private AtomicBoolean idlerIsIdle = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return idlerIsIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void setIdlerState(boolean bool) {
        idlerIsIdle.set(bool);
        if (bool && (resourceCallback != null)) {
            resourceCallback.onTransitionToIdle();
        }
    }
}
