package com.rasjdd.udacity.mybakingapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rasjdd.udacity.mybakingapp.R;
import com.rasjdd.udacity.mybakingapp.fragments.RecipeStepFragment;
import com.rasjdd.udacity.mybakingapp.models.Step;
import com.rasjdd.udacity.mybakingapp.utilities.Constants;

import java.util.List;

public class StepsFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Step> mSteps;

    public StepsFragmentPagerAdapter(Context context, List<Step> steps,
                                     FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = context;
        this.mSteps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(Constants.keyRecipeStep, mSteps.get(position));
        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position > 0) return mContext.getString(R.string.step) + " " + String.valueOf(position);
        return String.valueOf(mContext.getString(R.string.introduction));
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }


}
