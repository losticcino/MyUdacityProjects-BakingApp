package com.rasjdd.udacity.mybakingapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rasjdd.udacity.mybakingapp.AppSupervisor;
import com.rasjdd.udacity.mybakingapp.R;
import com.rasjdd.udacity.mybakingapp.StepDetailActivity;
import com.rasjdd.udacity.mybakingapp.adapters.RecipeListAdapter;
import com.rasjdd.udacity.mybakingapp.models.Recipe;
import com.rasjdd.udacity.mybakingapp.utilities.Constants;
import com.rasjdd.udacity.mybakingapp.utilities.NetUtils;
import com.rasjdd.udacity.mybakingapp.utilities.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A fragment representing a single Recipe detail screen.
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class RecipeListFragment extends Fragment {
//    /**
//     * The fragment argument representing the item ID that this fragment
//     * represents.
//     */

    private OnRecipeListClickListener mClickListener;
    private ArrayList<Recipe> mRecipes;
    private Recipe mRecipe;
    private RequestQueue mRequestQueue;
    private Boolean mLoading;
    private AppSupervisor appSupervisor;
    private boolean mTwoPane;

    //Declare views
    private RecyclerView mRecycler;
    private ProgressBar mProgressBar;
    private RecipeListAdapter mAdapter;

    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mRecipes == null) {
                getRecipeList(context);
                Toast.makeText(getContext(), String.valueOf(R.string.loading_data), Toast.LENGTH_LONG).show();
                mLoading = true;
            }
        }
    };


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mRecipes == null) mRecipes = new ArrayList<>();

        if (mRequestQueue == null) mRequestQueue = Volley.newRequestQueue(getContext());

//        if (getArguments().containsKey(Constants.keyFullRecipe)) {
//            mRecipe = (Recipe) getArguments().getSerializable(Constants.keyFullRecipe);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        mRecycler = rootView.findViewById(R.id.containerRecipeList);
        mRecycler.setHasFixedSize(true);

//        mAdapter = new RecipeListAdapter(this.getContext(), mClickListener);

        mProgressBar = rootView.findViewById(R.id.progressRecipeListFragment);

        if (mLoading) mProgressBar.setVisibility(View.VISIBLE);

//        if (mRecipe != null) {
//            for (Step step : mRecipe.getSteps()) {
//                ((TextView) rootView.findViewById(R.id.recipe_detail))
//                        .append(step.getDescription() + "\n\n");
//            }
//
//        }

        // Test for Two-Pane mode
        if (rootView.findViewById(R.id.containerRecipeStepsWide) != null) {
            mTwoPane = true;
        }

        // Idler for test
        if (getActivity() != null) {
            appSupervisor = (AppSupervisor) getActivity().getApplicationContext();
            appSupervisor.setIdleState(false);

            if (savedInstanceState != null && savedInstanceState.containsKey(Constants.keyRecipeList)) {
                mRecipes = (ArrayList<Recipe>) savedInstanceState.getSerializable(Constants.keyRecipeList);

                mRecycler.setAdapter( new RecipeListAdapter(getActivity().getApplicationContext(),
                        position -> mClickListener.onRecipeSelected(mRecipes.get(position))));
            }
        }

        return rootView;
    }


    // ---------- VOLLEY FUNCTION ----------

    private void getRecipeList(Context context) {
        if (NetUtils.testConnectivityBasic(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.RecipeListUrl,
                    new RecipeListFragment.webResponseListener(), new RecipeListFragment.webErrorListener());
            mRequestQueue.add(stringRequest);
        }
    }

//    @Override
//    public void onRecipeClick(Recipe recipe) {
//        Intent intent = new Intent(getActivity(), StepDetailActivity.class);
//        intent.putExtra(Constants.keyFullRecipe, recipe);
//        startActivity(intent);
//    }


    //


    // Volley Response Listener
    private class webResponseListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();
            Gson gson = gsonBuilder.create();

            Recipe[] recipes = gson.fromJson(response, Recipe[].class);
            Collections.addAll(mRecipes, recipes);

            mLoading = false;
            mProgressBar.setVisibility(View.GONE);

            // Do something already!
            mAdapter.setRecipeList(mRecipes);
        }
    }

    // Volley Error Listener
    private class webErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(), String.valueOf(error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeListClickListener) {
            mClickListener = (OnRecipeListClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnRecipeListClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mClickListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NetworkChangeReceiver.NETWORK_CHANGE_ACTION);

        if (getActivity() != null) {
            getActivity().registerReceiver(networkChangeReceiver, intentFilter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getActivity() != null) {
            getActivity().unregisterReceiver(networkChangeReceiver);
        }
    }

    public interface OnRecipeListClickListener {
        void onRecipeSelected(Recipe recipe);
    }
}
