package com.udacity.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;

/**
 * Created by sarabjeet on 30/3/17.
 */

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);

        }
    }

    public void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        setList(views, context, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.stock_list_view);
    }

    void setList(RemoteViews rv, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, WidgetRemoteViewService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        rv.setRemoteAdapter(R.id.stock_list_view, adapter);
    }
}
