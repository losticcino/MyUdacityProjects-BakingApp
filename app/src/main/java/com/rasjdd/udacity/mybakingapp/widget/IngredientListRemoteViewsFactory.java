package com.rasjdd.udacity.mybakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rasjdd.udacity.mybakingapp.R;
import com.rasjdd.udacity.mybakingapp.models.Ingredient;
import com.rasjdd.udacity.mybakingapp.models.Recipe;
import com.rasjdd.udacity.mybakingapp.utilities.AppUtilities;

public class IngredientListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Recipe mRecipe;

    IngredientListRemoteViewsFactory(Context context){
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipe = AppUtilities.loadRecipe(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews entry = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        // Make the ingredient entry pretty
        Ingredient ingredient = mRecipe.getIngredients().get(position);
        String s = ingredient.getIngredient() + " - " + String.valueOf(ingredient.getQuantity());
        if (s.endsWith(".0")) s = s.substring(0,s.length() - 2);
        if (!ingredient.getMeasure().toLowerCase().equals("unit")) s += " " + ingredient.getMeasure();
//        StyleSpan boldTypeFace = new StyleSpan(Typeface.BOLD);
//        SpannableStringBuilder ingredientBuilder = new SpannableStringBuilder(s);
//        ingredientBuilder.setSpan(boldTypeFace, 0, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        ingredientBuilder.append(" ").append(ingredient.getIngredient());
//
//        entry.setTextViewText(R.id.widget_textIngredient, ingredientBuilder);
        entry.setTextViewText(R.id.widget_textIngredient, s);

        return entry;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
