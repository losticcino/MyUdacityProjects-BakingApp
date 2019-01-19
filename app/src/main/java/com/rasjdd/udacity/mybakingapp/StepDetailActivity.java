package com.rasjdd.udacity.mybakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rasjdd.udacity.mybakingapp.adapters.StepsFragmentPagerAdapter;
import com.rasjdd.udacity.mybakingapp.databinding.ActivityStepDetailBinding;
import com.rasjdd.udacity.mybakingapp.models.Recipe;
import com.rasjdd.udacity.mybakingapp.utilities.Constants;
import com.rasjdd.udacity.mybakingapp.widget.IngredientListWidgetService;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 */
public class StepDetailActivity extends AppCompatActivity {
    private Recipe mRecipe;
    private int mStep;
    ActivityStepDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);
        Bundle bundle = getIntent().getExtras();

        // Set Toolbar
        Toolbar toolbar = mBinding.toolbarRecipeStepsPhone;
        setSupportActionBar(toolbar);

        if (bundle == null || !bundle.containsKey(Constants.keyFullRecipe)){

            Toast.makeText(getApplicationContext(),
                    getString(R.string.intent_failed),
                    Toast.LENGTH_LONG).show();
            NavUtils.navigateUpFromSameTask(this);
        }

        mRecipe = (Recipe) bundle.getSerializable(Constants.keyFullRecipe);
        mStep = bundle.getInt(Constants.keyRecipeStep, 0);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHideOffset(0);
        }

        StepsFragmentPagerAdapter pagerAdapter = new StepsFragmentPagerAdapter(getApplicationContext(),
                mRecipe.getSteps(), getSupportFragmentManager());
        mBinding.viewsRecipeSteps.setAdapter(pagerAdapter);
        mBinding.tabsRecipeSteps.setupWithViewPager(mBinding.viewsRecipeSteps);
        mBinding.viewsRecipeSteps.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                actionBar.setTitle(mRecipe.getSteps().get(i).getShortDescription());
                mStep = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mBinding.viewsRecipeSteps.setCurrentItem(mStep);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.recipe_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                navigateUpTo(new Intent(this, MainActivity.class));
                return true;
            case R.id.actionSendToWidget:
                IngredientListWidgetService.updateWidget(this, mRecipe);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.keyFullRecipe, mRecipe);
        outState.putInt(Constants.keyRecipeStep,mStep);
    }
}
