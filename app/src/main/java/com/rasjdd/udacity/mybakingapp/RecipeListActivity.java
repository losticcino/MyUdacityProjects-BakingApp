package com.rasjdd.udacity.mybakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rasjdd.udacity.mybakingapp.Models.Objects.Recipe;
import com.rasjdd.udacity.mybakingapp.Adapters.RecipeListViewAdapter;
import com.rasjdd.udacity.mybakingapp.Utilities.Constants;
import com.rasjdd.udacity.mybakingapp.Utilities.NetUtils;

import java.util.ArrayList;

/**
 * An activity representing a list of RecipeList. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity implements RecipeListViewAdapter.RecipeListOnClickHandler {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ArrayList<Recipe> mRecipeList;
    private RequestQueue mRequestQueue;
    private Gson gRecipeList;
    private RecipeListViewAdapter mViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mViewAdapter = new RecipeListViewAdapter(this::onRecipeClick);
        recyclerView = findViewById(R.id.containerRecipeList);
        recyclerView.setAdapter(mViewAdapter);

        if (mRequestQueue == null) mRequestQueue = Volley.newRequestQueue(this);
        
        if (mRecipeList == null) {
            mRecipeList = new ArrayList<>();
            getRecipeList();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gRecipeList = gsonBuilder.create();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRecipeList);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.containerRecipeStepsWide) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.containerRecipeList);
        assert recyclerView != null;
//        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void getRecipeList() {
        if (NetUtils.testConnectivityBasic(this)) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.RecipeListUrl,
                    new webResponseListener(), new webErrorListener());
            mRequestQueue.add(stringRequest);
        }
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
//                    arguments.putInt(Constants.keyFullRecipe, recipe.getId());
            arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, String.valueOf(recipe.getId()));
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerRecipeStepsWide, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, String.valueOf(recipe.getId()));

            startActivity(intent);
        }
    }

//    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
//        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, mRecipeList, mTwoPane));
//    }



//    public class SimpleItemRecyclerViewAdapter
//            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
//
//        private final RecipeListActivity mParentActivity;
//        private ArrayList<Recipe> mValues;
//        private final boolean mTwoPane;
//        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Recipe recipe = (Recipe) view.getTag();
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
////                    arguments.putInt(Constants.keyFullRecipe, recipe.getId());
//                    arguments.putInt(RecipeDetailFragment.ARG_ITEM_ID, recipe.getId());
//                    RecipeDetailFragment fragment = new RecipeDetailFragment();
//                    fragment.setArguments(arguments);
//                    mParentActivity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.containerRecipeStepsWide, fragment)
//                            .commit();
//                } else {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, RecipeDetailActivity.class);
//                    intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, recipe.getId());
//
//                    context.startActivity(intent);
//                }
//            }
//        };
//
//        SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
//                                      ArrayList<Recipe> items,
//                                      boolean twoPane) {
//            mValues = items;
//            mParentActivity = parent;
//            mTwoPane = twoPane;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.recipe_list_content, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            holder.mIdView.setText(mValues.get(position).getName());
//            holder.mContentView.setText(mValues.get(position).getName());
//
//            holder.itemView.setTag(mValues.get(position));
//            holder.itemView.setOnClickListener(mOnClickListener);
//        }
//
//        @Override
//        public int getItemCount() {
//            return 0;
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            final TextView mIdView;
//            final TextView mContentView;
//
//            ViewHolder(View view) {
//                super(view);
//                mIdView = (TextView) view.findViewById(R.id.id_text);
//                mContentView = (TextView) view.findViewById(R.id.content);
//            }
//        }
//
//        public void setRecipeList(ArrayList<Recipe> recipes) {
//            mValues = recipes;
//            notifyDataSetChanged();
//        }
//    }

    // ---------- VOLLEY FUNCTION ----------

    // Volley Response Listener
    private class webResponseListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            Recipe[] recipes = gRecipeList.fromJson(response, Recipe[].class);
            for (Recipe recipe : recipes) {
                mRecipeList.add(recipe);
            }
            mViewAdapter.setRecipeList(mRecipeList);
        }
    }

    // Volley Error Listener
    private class webErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), String.valueOf(error), Toast.LENGTH_LONG).show();
        }
    }
}
