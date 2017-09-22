package com.example.gabriel.bakingapp.Widget;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.gabriel.bakingapp.R;

import java.util.ArrayList;

public class ListWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListRemoteViewsFactory(this.getApplicationContext(),
                intent.getStringArrayListExtra("LIST") );
    }

    private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        ArrayList<String> mList;
        Context mContext;
        ListRemoteViewsFactory(Context context, ArrayList<String> list){
                mList = list;
                mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_row);
            row.setTextViewText(R.id.ingredient_row_TextView, mList.get(position));

            return row;
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
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
