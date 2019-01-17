package com.rasjdd.udacity.mybakingapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rasjdd.udacity.mybakingapp.Models.Objects.Recipe;
import com.rasjdd.udacity.mybakingapp.R;

import java.util.ArrayList;

public class RecipeListViewAdapter extends RecyclerView.Adapter<RecipeListViewAdapter.RecipeListViewHolder> {
    private final RecipeListOnClickHandler mClickHandler;
    private ArrayList<Recipe> mRecipes;

    public RecipeListViewAdapter(RecipeListOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mRecipes == null) mRecipes= new ArrayList<>();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_list_content, viewGroup, false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder recipeListViewHolder, int i) {
        recipeListViewHolder.mNameView.setText(mRecipes.get(i).getName());
        recipeListViewHolder.mContentView.setText(mRecipes.get(i).getName());
        recipeListViewHolder.itemView.setTag(mRecipes.get(i));
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null){
            return 0;
        }
        else {
            return mRecipes.size();
        }
    }

    public interface RecipeListOnClickHandler {
        void onRecipeClick(Recipe recipe);
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mNameView;
        final TextView mContentView;

        RecipeListViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameView = (TextView) itemView.findViewById(R.id.id_text);
            mContentView = (TextView) itemView.findViewById(R.id.content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = mRecipes.get(getAdapterPosition());
            mClickHandler.onRecipeClick(recipe);
        }
    }

    public void setRecipeList(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public void addRecipeToList(Recipe recipe) {
        mRecipes.add(recipe);
        notifyDataSetChanged();
    }
}
