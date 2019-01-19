package com.rasjdd.udacity.mybakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.rasjdd.udacity.mybakingapp.adapters.StepsFragmentPagerAdapter;
import com.rasjdd.udacity.mybakingapp.adapters.StepsListViewAdapter;
import com.rasjdd.udacity.mybakingapp.fragments.RecipeStepFragment;
import com.rasjdd.udacity.mybakingapp.models.Recipe;
import com.rasjdd.udacity.mybakingapp.utilities.Constants;
import com.rasjdd.udacity.mybakingapp.widget.IngredientListWidgetService;

import org.w3c.dom.Text;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 */
public class StepDetailActivity extends AppCompatActivity implements StepsListViewAdapter.StepListOnClickHandler {
    private Recipe mRecipe;
    private int mStepNumber;
//    ActivityStepDetailBinding mDetailBinding;
//    ActivityStepListBinding mListBinding;
    StepsListViewAdapter mRecyclerHolderAdapter;
    private boolean mTwoPane;
    private boolean mFistAccess = true;

    // Views
    private RecyclerView mContainerStepsList;
    private FrameLayout mContainerDetailView;
    private Toolbar mToolBarPaging;
    private TabLayout mTabBarPaging;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_step_list);

        mContainerStepsList = findViewById(R.id.containerStepsList);
        mContainerDetailView = findViewById(R.id.containerStepsFragmentWide);
        mToolBarPaging = findViewById(R.id.toolbarRecipeStepsPhone);
        mTabBarPaging = findViewById(R.id.tabsRecipeSteps);
        mViewPager = findViewById(R.id.viewsRecipeSteps);
        Bundle bundle = getIntent().getExtras();

//        String s = String.valueOf(getTitle());
//        setTitle(mRecipe.getName().toCharArray());
//        Toolbar toolbar = mDetailBinding.toolbarRecipeStepsPhone;
//        setSupportActionBar(toolbar);

        // init recycler
        mRecyclerHolderAdapter = new StepsListViewAdapter(this::onStepClick);
        mContainerStepsList.setHasFixedSize(true);
        mContainerStepsList.setAdapter(mRecyclerHolderAdapter);

        if (bundle == null || !bundle.containsKey(Constants.keyFullRecipe)){

            Toast.makeText(getApplicationContext(),
                    getString(R.string.intent_failed),
                    Toast.LENGTH_LONG).show();
            NavUtils.navigateUpFromSameTask(this);
        }

        if (bundle.containsKey(Constants.keyLayoutMode)) {
            mTwoPane = bundle.getBoolean(Constants.keyLayoutMode);
        }
        else if (findViewById(R.id.containerStepsFragmentWide) != null) {
            mTwoPane = true;
        }
        if (bundle.containsKey(Constants.keyFullRecipe)) mRecipe = (Recipe) bundle.getSerializable(Constants.keyFullRecipe);
        if (mFistAccess) {
            mStepNumber = bundle.getInt(Constants.keyStepNumber, 0);
            mFistAccess = false;
        }

        mRecyclerHolderAdapter.setStepList(mRecipe.getSteps());

        // Show the Up button in the action bar.
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setTitle(mRecipe.getName());
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHideOffset(0);
//        }

        if (mTwoPane) {
            // ---------- Fragment Pager for the Step Details Views ---------

            StepsFragmentPagerAdapter pagerAdapter = new StepsFragmentPagerAdapter(getApplicationContext(),
                    mRecipe.getSteps(), getSupportFragmentManager());
            mViewPager.setAdapter(pagerAdapter);
            mTabBarPaging.setupWithViewPager(mViewPager);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    setTitle(mRecipe.getSteps().get(i).getShortDescription());
                    mStepNumber = i;
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

            mViewPager.setCurrentItem(mStepNumber);
        }
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
        outState.putInt(Constants.keyStepNumber, mStepNumber);
    }

    @Override
    public void onStepClick(int id) {
        this.mStepNumber = id;
        mViewPager.setCurrentItem(mStepNumber);

        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(Constants.keyRecipeStep, mRecipe.getSteps().get(mStepNumber));
            RecipeStepFragment fragment = new RecipeStepFragment();
            fragment.setArguments(arguments);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerStepsFragmentWide, fragment)
                    .commit();
        } else {

            Intent intent = new Intent(this, StepDetailActivitySingle.class);
            intent.putExtra(Constants.keyFullRecipe,mRecipe);
            intent.putExtra(Constants.keyStepNumber,mStepNumber);

            startActivity(intent);
        }
    }
}
