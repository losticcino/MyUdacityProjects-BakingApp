//package com.rasjdd.udacity.mybakingapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.rasjdd.udacity.mybakingapp.adapters.RecipeListViewAdapter;
//import com.rasjdd.udacity.mybakingapp.fragments.RecipeStepFragment;
//import com.rasjdd.udacity.mybakingapp.models.Recipe;
//import com.rasjdd.udacity.mybakingapp.utilities.AppUtilities;
//import com.rasjdd.udacity.mybakingapp.utilities.Constants;
//import com.rasjdd.udacity.mybakingapp.utilities.NetUtils;
//import com.rasjdd.udacity.mybakingapp.widget.IngredientListWidgetService;
//
//import java.util.ArrayList;
//
///**
// * An activity representing a list of RecipeList. This activity
// * has different presentations for handset and tablet-size devices. On
// * handsets, the activity presents a list of items, which when touched,
// * lead to a {@link StepDetailActivity} representing
// * item details. On tablets, the activity presents the list of items and
// * item details side-by-side using two vertical panes.
// */
//public class RecipeListActivity extends AppCompatActivity implements RecipeListViewAdapter.RecipeListOnClickHandler {
//
//    /**
//     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
//     * device.
//     */
//    private boolean mTwoPane;
//    private ArrayList<Recipe> mRecipeList;
//    private RequestQueue mRequestQueue;
//    private Gson gRecipeList;
////    private RecipeListViewAdapter mViewAdapter;
//    private RecyclerView recyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recipe_list);
//
////        mViewAdapter = new RecipeListViewAdapter(this::onRecipeClick);
//        recyclerView = findViewById(R.id.containerRecipeList);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(mViewAdapter);
//
//        if (mRequestQueue == null) mRequestQueue = Volley.newRequestQueue(this);
//
//        if (mRecipeList == null) {
//            mRecipeList = new ArrayList<>();
//            getRecipeList();
//        }
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.serializeNulls();
//        gRecipeList = gsonBuilder.create();
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRecipeList);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());
//
//        if (findViewById(R.id.containerRecipeStepsWide) != null) {
//            mTwoPane = true;
//        }
//
//        View recyclerView = findViewById(R.id.containerRecipeList);
//        assert recyclerView != null;
//    }
//
//    private void getRecipeList() {
//        if (NetUtils.testConnectivityBasic(this)) {
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.RecipeListUrl,
//                    new webResponseListener(), new webErrorListener());
//            mRequestQueue.add(stringRequest);
//        }
//    }
//
//    // Actions to perform on click
//    @Override
//    public void onRecipeClick(Recipe recipe) {
//        AppUtilities.saveRecipe(this,recipe);
//        IngredientListWidgetService.updateWidget(this, recipe);
//
//        if (mTwoPane) {
//            Bundle arguments = new Bundle();
//            arguments.putSerializable(Constants.keyFullRecipe, recipe);
//            RecipeStepFragment fragment = new RecipeStepFragment();
//            fragment.setArguments(arguments);
//            this.getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.containerRecipeStepsWide, fragment)
//                    .commit();
//        } else {
//            Intent intent = new Intent(this, StepDetailActivity.class);
//            intent.putExtra(Constants.keyFullRecipe,recipe);
//
//            startActivity(intent);
//        }
//    }
//
//    // ---------- VOLLEY FUNCTION ----------
//
//    // Volley Response Listener
//    private class webResponseListener implements Response.Listener<String> {
//        @Override
//        public void onResponse(String response) {
//            Recipe[] recipes = gRecipeList.fromJson(response, Recipe[].class);
//            for (Recipe recipe : recipes) {
//                mRecipeList.add(recipe);
//            }
////            mViewAdapter.setRecipeList(mRecipeList);
//        }
//    }
//
//    // Volley Error Listener
//    private class webErrorListener implements Response.ErrorListener {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            Toast.makeText(getApplicationContext(), String.valueOf(error), Toast.LENGTH_LONG).show();
//        }
//    }
//}
