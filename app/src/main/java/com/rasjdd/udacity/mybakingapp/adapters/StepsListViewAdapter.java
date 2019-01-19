package com.rasjdd.udacity.mybakingapp.adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rasjdd.udacity.mybakingapp.R;
import com.rasjdd.udacity.mybakingapp.models.Recipe;
import com.rasjdd.udacity.mybakingapp.utilities.NetUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StepsListViewAdapter extends RecyclerView.Adapter<StepsListViewAdapter.StepsListViewHolder> {
    private final StepListOnClickHandler mClickHandler;
    private ArrayList<Recipe> mRecipes;
    private Resources resources;

    public StepsListViewAdapter(StepListOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public StepsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mRecipes == null) mRecipes= new ArrayList<>();
        resources = viewGroup.getResources();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_card, viewGroup, false);
        return new StepsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsListViewHolder stepsListViewHolder, int i) {
        Recipe recipe = mRecipes.get(i);

        stepsListViewHolder.mNameView.setText(recipe.getName());

        String s = resources.getString(R.string.servings_tag) + ": "+ String.valueOf((int) recipe.getServings());
        stepsListViewHolder.mDetailView.setText(s);

        if (NetUtils.isGraphicFileformat(recipe.getImage())) {
            Picasso.get()
                    .load(recipe.getImage())
                    .placeholder(R.drawable.bread_icon)
                    .into(stepsListViewHolder.mImagePreview);
        } else {
            stepsListViewHolder.mImagePreview.setImageResource(R.drawable.bread_icon);
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

    public interface StepListOnClickHandler {
        void onStepClick(Recipe recipe);
    }

    public class StepsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mNameView;
        final TextView mDetailView;
        final ImageView mImagePreview;

        StepsListViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameView = (TextView) itemView.findViewById(R.id.textRecipeCardName);
            mDetailView = (TextView) itemView.findViewById(R.id.textRecipeCardDetails);
            mImagePreview = (ImageView) itemView.findViewById(R.id.imageRecipeCardPreview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = mRecipes.get(getAdapterPosition());
            mClickHandler.onStepClick(recipe);
        }
    }

    public void setRecipeList(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public void addStepToList(Recipe recipe) {
        mRecipes.add(recipe);
        notifyDataSetChanged();
    }
}
