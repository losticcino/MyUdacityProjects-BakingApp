package com.rasjdd.udacity.mybakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.rasjdd.udacity.mybakingapp.R;
import com.rasjdd.udacity.mybakingapp.models.Recipe;
import com.rasjdd.udacity.mybakingapp.utilities.AppUtilities;

public class IngredientListWidgetService extends RemoteViewsService {

    public static void updateWidget(Context context, Recipe recipe) {
        AppUtilities.saveRecipe(context, recipe);
        Toast.makeText(context, String.valueOf(context.getResources().getString(R.string.widget_updated)),
                Toast.LENGTH_LONG).show();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIDs = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientListWidget.class));
        IngredientListWidget.updateAllWidgets(context, appWidgetManager, appWidgetIDs);
    }

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new IngredientListRemoteViewsFactory(getApplicationContext());
    }
}
