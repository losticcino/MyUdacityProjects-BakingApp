package com.rasjdd.udacity.mybakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.rasjdd.udacity.mybakingapp.models.Recipe;
import com.rasjdd.udacity.mybakingapp.R;
import com.rasjdd.udacity.mybakingapp.RecipeListActivity;
import com.rasjdd.udacity.mybakingapp.utilities.AppUtilities;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientListWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Recipe recipe = AppUtilities.loadRecipe(context);

        // If there's data,
        if (recipe != null) {
            // Do stuff
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, RecipeListActivity.class), 0);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

            // Set header and listen for a click to launch the app
            remoteViews.setTextViewText(R.id.widget_textHeader, recipe.getName());
            remoteViews.setOnClickPendingIntent(R.id.widget_textHeader, pendingIntent);

            Intent intent = new Intent(context, IngredientListWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);


            remoteViews.setRemoteAdapter(R.id.widget_containerIngredientList, intent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_containerIngredientList);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAllWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

