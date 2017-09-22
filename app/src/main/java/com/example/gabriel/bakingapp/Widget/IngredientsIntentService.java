package com.example.gabriel.bakingapp.Widget;


import android.annotation.TargetApi;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.gabriel.bakingapp.R;

import java.util.ArrayList;

public class IngredientsIntentService extends IntentService{

    public static final String ACTION_PUT_ING = "com.example.gabriel.BakingApp.action.put_ing";

    public static void startActionPutIngredients(Context context, ArrayList<String> mIngList){

        Intent intent = new Intent(context, IngredientsIntentService.class);
        intent.setAction(ACTION_PUT_ING);
        intent.putStringArrayListExtra("LIST", mIngList);
        context.startService(intent);
    }

    public IngredientsIntentService() {
        super("IngredientsIntentService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param intent Used to name the worker thread, important only for debugging.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_PUT_ING == action){
                final ArrayList<String> listExtra = intent.getStringArrayListExtra("LIST");
                    handleActionIngredients(listExtra);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void handleActionIngredients(ArrayList<String> list){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        Bundle options = new Bundle();
        options.putStringArrayList("LIST", list);
        if(appWidgetIds.length > 0) {
            appWidgetManager.updateAppWidgetOptions(appWidgetIds[0], options);

            RecipeWidgetProvider.updateRecipeWidget(this, appWidgetManager, appWidgetIds);
        }

    }
}
