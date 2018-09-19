//package com.dev.nicola.allweather.ui.widget
//
//import android.app.PendingIntent
//import android.appwidget.AppWidgetManager
//import android.appwidget.AppWidgetProvider
//import android.content.ComponentName
//import android.content.Context
//import android.content.Intent
//import android.widget.RemoteViews
//
//import com.dev.nicola.allweather.R
//import com.dev.nicola.allweather.ui.activity.MainActivity
//
//class WidgetProvider : AppWidgetProvider() {
//
//    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
//
//        val mWidget = ComponentName(context, WidgetProvider::class.java)
//
//        val remoteViews = RemoteViews(context.packageName, R.layout.app_widget)
//        remoteViews.setTextViewText(R.id.location_widget, "Sossano")
//        remoteViews.setTextViewText(R.id.condition_widget, "Sereno")
//        remoteViews.setTextViewText(R.id.temperature_widget, "35°")
//
//        val configIntent = Intent(context, MainActivity::class.java)
//
//        val configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0)
//
//        remoteViews.setOnClickPendingIntent(R.id.widget_layout, configPendingIntent)
//
//        appWidgetManager.updateAppWidget(mWidget, remoteViews)
//
//    }
//}