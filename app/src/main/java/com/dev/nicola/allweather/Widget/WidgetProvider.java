package com.dev.nicola.allweather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.dev.nicola.allweather.ui.activity.MainActivity;
import com.dev.nicola.allweather.R;

/**
 * Created by Nicola on 05/08/2016.
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        ComponentName mWidget = new ComponentName(context, WidgetProvider.class);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        remoteViews.setTextViewText(R.id.location_widget, "Sossano");
        remoteViews.setTextViewText(R.id.condition_widget, "Sereno");
        remoteViews.setTextViewText(R.id.temperature_widget, "35°");

        Intent configIntent = new Intent(context, MainActivity.class);

        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.widget_layout, configPendingIntent);

        appWidgetManager.updateAppWidget(mWidget, remoteViews);

    }

}
