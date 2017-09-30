package com.example.gabriel.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.gabriel.bakingapp.R;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Bundle option = appWidgetManager.getAppWidgetOptions(appWidgetId);
        ArrayList<String> mList = option.getStringArrayList("LIST");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putStringArrayListExtra("LIST", mList);
        intent.putExtra("Random", Math.random() * 1000);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.appwidget_ListView, intent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

