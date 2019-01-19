package com.rasjdd.udacity.mybakingapp.adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rasjdd.udacity.mybakingapp.R;
import com.rasjdd.udacity.mybakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsListViewAdapter extends RecyclerView.Adapter<StepsListViewAdapter.StepsListViewHolder> {
    private final StepListOnClickHandler mClickHandler;
    private List<Step> mSteps;
    private Resources resources;

    public StepsListViewAdapter(StepListOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public StepsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mSteps == null) mSteps = new ArrayList<>();
        resources = viewGroup.getResources();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.step_card, viewGroup, false);
        return new StepsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsListViewHolder stepsListViewHolder, int i) {
        Step step = mSteps.get(i);
        String s;

        stepsListViewHolder.mNameView.setText(step.getShortDescription());

        if (step.getId() == 0) s = String.valueOf(resources.getString(R.string.introduction));
        else s = String.valueOf(step.getId());
        s = String.valueOf(resources.getString(R.string.step)) + ": " + s;
        stepsListViewHolder.mDetailView.setText(s);
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) {
            return 0;
        } else {
            return mSteps.size();
        }
    }

    public interface StepListOnClickHandler {
        void onStepClick(int id);
    }

    public class StepsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mNameView;
        final TextView mDetailView;

        StepsListViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameView = itemView.findViewById(R.id.textStepCardName);
            mDetailView = itemView.findViewById(R.id.textStepCardDetails);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = mSteps.get(getAdapterPosition()).getId();
            mClickHandler.onStepClick(id);
        }
    }

    public void setStepList(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    public void addStepToList(Step step) {
        mSteps.add(step);
        notifyDataSetChanged();
    }
}
