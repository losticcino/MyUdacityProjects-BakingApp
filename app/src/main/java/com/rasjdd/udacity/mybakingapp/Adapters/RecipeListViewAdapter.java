package com.rasjdd.udacity.mybakingapp.Adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rasjdd.udacity.mybakingapp.Models.Objects.Recipe;
import com.rasjdd.udacity.mybakingapp.R;
import com.rasjdd.udacity.mybakingapp.Utilities.NetUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeListViewAdapter extends RecyclerView.Adapter<RecipeListViewAdapter.RecipeListViewHolder> {
    private final RecipeListOnClickHandler mClickHandler;
    private ArrayList<Recipe> mRecipes;
    private Resources resources;

    public RecipeListViewAdapter(RecipeListOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mRecipes == null) mRecipes= new ArrayList<>();
        resources = viewGroup.getResources();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_card, viewGroup, false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder recipeListViewHolder, int i) {
        recipeListViewHolder.mNameView.setText(mRecipes.get(i).getName());
        recipeListViewHolder.mDetailView.setText(resources.getString(R.string.servings_tag) + ": "
                + String.valueOf((int) mRecipes.get(i).getServings()));

        if (NetUtils.isGraphicFileformat(mRecipes.get(i).getImage())) {
            Picasso.get()
                    .load(mRecipes.get(i).getImage())
                    .placeholder(R.drawable.bread_icon)
                    .into(recipeListViewHolder.mImagePreview);
        } else {
            Picasso.get()
                    .load(R.drawable.bread_icon)
                    .into(recipeListViewHolder.mImagePreview);
        }
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
        final TextView mDetailView;
        final ImageView mImagePreview;

        RecipeListViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameView = (TextView) itemView.findViewById(R.id.textRecipeCardName);
            mDetailView = (TextView) itemView.findViewById(R.id.textRecipeCardDetails);
            mImagePreview = (ImageView) itemView.findViewById(R.id.imageRecipeCardPreview);
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
